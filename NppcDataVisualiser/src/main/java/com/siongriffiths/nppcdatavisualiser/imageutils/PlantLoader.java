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

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Component //Component scanning will find this and treat class as bean
public class PlantLoader {

    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantImageManager plantImageManager;
    @Autowired
    private PlantDayManager plantDayManager;
    @Autowired
    private ExperimentManager experimentManager;

    @Value("${image-repository.root.dir}")
    private String imageRepoRoot;


    @Value("${image-repository.root.dir.suffix}")
    private String imageRepoSuffix;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void initPlantImages(Experiment experiment) {
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



    private String normaliseFilePath(String pathToNormalise){
        return FilenameUtils.separatorsToUnix(pathToNormalise);
    }

    private String removeExcessDirectoryPath(String path){
        return path.replace(imageRepoRoot,"");
    }

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

    private void processDirectory(File[] directories, Experiment experiment){
        for (File plantDir : directories) {

            String barCode = plantDir.getName();

            Plant plant = plantManager.createOrGetPlantByBarcode(barCode);

            File[] cameraTypeFiles = plantDir.listFiles();

            if (cameraTypeFiles != null) {
                for(File cameraTypeFile : cameraTypeFiles){
                    String cameraType = cameraTypeFile.getName();

                    // TODO: 09/03/2016 change this to use a property or filter
                    if(cameraType.equalsIgnoreCase("nir")){
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
                                            String path = normaliseFilePath(plantImageFile.getPath());
                                            path = removeExcessDirectoryPath(path);
                                            Date date = extractDateFromImageName(plantImageFile.getName());
                                            PlantImage plantImage = plantImageManager.getOrCreatePlantImageByPath(path);
                                            plantImage.setFilePath(path);
                                            plantDayManager.addToOrCreatePlantDay(date,plantImage,plant);



                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Sort days in date order. Some instances where this is not the case otehrwise..
            Collections.sort(plant.getPlantDays());
            experimentManager.saveExperiment(experiment); //save experiment since we cannot save plant with transient reference
            plant.setExperiment(experiment);
            plantManager.savePlant(plant); //cascade save all the way down
            logger.info("created plant " + plant.getBarCode());
            experiment.addPlant(plant);
        }
        experiment.setStatus(ExperimentStatus.INITIALISED);
        experimentManager.saveExperiment(experiment);
    }
}




