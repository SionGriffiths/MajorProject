package com.siongriffiths.nppcdatavisualiser.imageutils;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Component //Component scanning will find this and treat class as bean
public class ImageLoader {

    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantImageManager plantImageManager;
    @Autowired
    private PlantDayManager plantDayManager;

    @Value("${image-repository.root.dir}")
    private String imageRepoRoot;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


//    build list of files then pass to another method to make the plants? quicker?
//    public void initPlantImages(){
//        File dir = new File(imageRepoRoot);
//        Collection files = FileUtils.listFiles(
//                dir,
//                new RegexFileFilter("^(.*?)"),
//                DirectoryFileFilter.DIRECTORY
//        );
//        logger.info("loaded dirs");
//    }


    public void initPlantImages() {
        File dir = new File(imageRepoRoot);
        File[] directories = dir.listFiles();
        if (directories != null) {

            for (File plantDir : directories) {
                //create new plant with barcode = dirname
                Plant plant = new Plant(plantDir.getName());

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
//                                String viewType = viewTypeFile.getName();

                                File[] angleOptionFiles = viewTypeFile.listFiles();
                                if(angleOptionFiles != null){
                                    for(File angleOptionFile : angleOptionFiles){
//                                        String angleOption = angleOptionFile.getName();

                                        File[] plantImageFiles = angleOptionFile.listFiles();
                                        if(plantImageFiles != null){

                                            for(File plantImageFile : plantImageFiles){
                                                String path = normaliseFilePath(plantImageFile.getPath());
                                                path = removeSurplusDirectoryGibberish(path);
                                                Date date = extractDateFromImageName(plantImageFile.getName());
                                                PlantImage plantImage = new PlantImage();
                                                plantImage.setFilePath(path);
                                                plantDayManager.addToOrCreatePlantDay(date,plantImage,plant);
                                                // TODO: 09/03/2016 A less naive method of doing this would be lovely
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Sort days in date order.
                Collections.sort(plant.getPlantDays());
                plantManager.savePlant(plant); //cascade save all the way down
                logger.info("created plant " + plant.getBarCode());
            }
        }
    }



    private String normaliseFilePath(String pathToNormalise){
        return FilenameUtils.separatorsToUnix(pathToNormalise);
    }

    private String removeSurplusDirectoryGibberish(String path){
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
}