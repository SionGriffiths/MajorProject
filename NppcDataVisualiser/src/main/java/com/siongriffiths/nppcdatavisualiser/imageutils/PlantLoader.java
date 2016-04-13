package com.siongriffiths.nppcdatavisualiser.imageutils;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantLoader is a utility bean which builds the major domain models for the system in the form of plants and plant images.
 * This class is tightly coupled to the NPPC data repository and in particular the directory structure of the analysed data
 * directories. Recursive methods are available (and faster steam based solutions in java8) but are considerably slower in
 * practise than the naiive method implemented within this class.
 */

@Component
public class PlantLoader {

    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;

    /**
     * PlantImageManager, a service class providing access to business logic and persistence for PlantImage objects
     */
    @Autowired
    private PlantImageManager plantImageManager;

    /**
     * PlantDayManager, a service class providing access to business logic and persistence for PlantDay objects
     */
    @Autowired
    private PlantDayManager plantDayManager;

    /**
     * ExperimentManager, a service class providing access to business logic and persistence for Experiment objects
     */
    @Autowired
    private ExperimentManager experimentManager;

    /**
     * Property value found in default property file.
     * Contains the root directory for experiment image files
     */
    @Value("${image-repository.root.dir}")
    private String imageRepoRoot;

    /**
     * Property value found in default property file.
     * Contains the directory structure between the above root directory and the image files
     * Needed in order to remove excess directory path for serving static content
     */
    @Value("${image-repository.root.dir.suffix}")
    private String imageRepoSuffix;

    /**
     * Property value found in default property file.
     * Contains a list of image modalities to be ignored when adding images to the system
     */
    @Value("#{'${ignored.image.modalities}'.split(',')}")
    private Set<String> ignoredImageModalities;


    /**
     * Logger instance for this class
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Initial experiment initialise method, ensures the target directory exists for the given experiment
     * before attempting further processing
     * @param experiment the target experiment
     */
    public void initExperiment(Experiment experiment) {
        File dir = new File(imageRepoRoot + experiment.getExperimentCode() + imageRepoSuffix);
        if (dir.exists()) {
            File[] directories = dir.listFiles();
            if (directories != null) {
                processDirectory(directories,experiment);
            }
        } else {
            experiment.setStatus(ExperimentStatus.NOT_INITIALISED);
            experimentManager.saveExperiment(experiment);
        }
    }


    /**
     * Method ensures full compatibility with windows systems which insist on using different slashes for directory
     * listing. Java native File class usually handles the discrepancy but not if the path is saved as a standard string
     * in the database, hence this safety method.
     * @param pathToNormalise The filepath as String to normalise
     * @return normalised file path
     */
    private String normaliseFilePath(String pathToNormalise){
        return FilenameUtils.separatorsToUnix(pathToNormalise);
    }

    /**
     * Trim full file path to retain only what's required to serve the images from a spring resource repository as opposed
     * to direct access to the images with the full path.
     * @param path File path to be trimmed
     * @return trimmed file path
     */
    private String removeExcessDirectoryPath(String path){
        return path.replace(imageRepoRoot,"");
    }

    /**
     * Extract a correctly formatted Date object from the filename of the image files
     * @param filename the filename
     * @return a Date object extracted from the filename
     */
    private Date extractDateFromImageName(String filename){
        String trimmedName = FilenameUtils.removeExtension(filename);
        trimmedName = trimmedName.substring(0,trimmedName.length() - 2);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date =  formatter.parse(trimmedName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     *
     * @param directories
     * @param experiment
     */
    private void processDirectory(File[] directories, Experiment experiment){

        long startTime = System.currentTimeMillis();

        for (File plantDir : directories) {

            String barCode = plantDir.getName();

            Plant plant = plantManager.createOrGetPlantByBarcode(barCode);
            File[] cameraTypeFiles = plantDir.listFiles();
            if (cameraTypeFiles != null) {
                for(File cameraTypeFile : cameraTypeFiles){
                    String cameraType = cameraTypeFile.getName();
                    if(ignoredImageModalities.contains(cameraType.toLowerCase()) || ignoredImageModalities.contains(cameraType.toUpperCase())){
                        continue;
                    }
                    File[] viewTypeFiles = cameraTypeFile.listFiles();
                    if(viewTypeFiles != null){
                        for(File viewTypeFile : viewTypeFiles){
                            File[] angleOptionFiles = viewTypeFile.listFiles();
                            if(angleOptionFiles != null){
                                for(File angleOptionFile : angleOptionFiles){
                                    File[] plantImageFiles = angleOptionFile.listFiles();
                                    if(plantImageFiles != null){
                                        for(File plantImageFile : plantImageFiles){
                                            createPlantImages(plantImageFile,plant);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            persistPlants(plant,experiment);
        }
        experiment.setStatus(ExperimentStatus.INITIALISED);
        experimentManager.saveExperiment(experiment);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        logger.info("Experiement " + experiment.getExperimentCode() + " initialised");
        logger.info("Initialisation time was " + elapsedTime/1000 + " seconds");

    }

    /**
     * Creates a plant image from current file and add to or creates a new PlantDay based on the date of the plantImage
     * @param plantImageFile File corresponding to the plant image
     * @param plant plant associated with the image file
     */
    private void createPlantImages(File plantImageFile, Plant plant){
        String path = normaliseFilePath(plantImageFile.getPath());
        path = removeExcessDirectoryPath(path);
        Date date = extractDateFromImageName(plantImageFile.getName());
        PlantImage plantImage = plantImageManager.getOrCreatePlantImageByPath(path);
        plantImage.setFilePath(path);
        plantDayManager.addToOrCreatePlantDay(date,plantImage,plant);
    }

    /**
     * Persists the current plant instance, invokes a cascade save of plantDays and plantImages
     * @param plant the current plant
     * @param experiment the current experiment
     */
    private void persistPlants(Plant plant, Experiment experiment ){
        //Sort days in date order. Some instances where this is not the case otehrwise..
        Collections.sort(plant.getPlantDays());
        experimentManager.saveExperiment(experiment); //save experiment since we cannot save plant with transient reference
        plant.setExperiment(experiment);
        plantManager.savePlant(plant); //cascade save all the way down
        logger.info("created plant " + plant.getBarCode());
        experiment.addPlant(plant);
    }






//    private void processDirectory(File[] directories, Experiment experiment) {
//
//        long startTime = System.currentTimeMillis();
//
//        for (File plantDir : directories) {
//            String barCode = plantDir.getName();
//            Plant plant = plantManager.createOrGetPlantByBarcode(barCode);
//
//            List<File> files = new ArrayList<>();
//            listFileTree(plantDir,files);
//            for(File file : files){
//                createPlantImages(file,plant);
//            }
//
//            persistPlants(plant,experiment);
//        }
//
//        experiment.setStatus(ExperimentStatus.INITIALISED);
//        experimentManager.saveExperiment(experiment);
//
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//
//        logger.info("Experiement " + experiment.getExperimentCode() + " initialised");
//        logger.info("Initialisation time was" + elapsedTime);
//
//
//
//    }

//    private Collection<File> listFileTree(File dir) {
//        Set<File> fileTree = new HashSet<File>();
//        for (File entry : dir.listFiles()) {
//            if (entry.isFile()){
//                fileTree.add(entry);
//            }else{
//                fileTree.addAll(listFileTree(entry));
//            }
//        }
//        return fileTree;
//    }

//
//    private void listFileTree(File dir, List<File> files) {
//
//        File[] list = dir.listFiles();
//
//        if (list == null) return;
//
//        for ( File f : list ) {
//            if ( f.isDirectory() ) {
//                listFileTree( f, files );
////                System.out.println( "Dir:" + f.getAbsoluteFile() );
//            }
//            else {
////                System.out.println( "File:" + f.getAbsoluteFile() );
//                files.add(f);
//            }
//        }
//    }
}




