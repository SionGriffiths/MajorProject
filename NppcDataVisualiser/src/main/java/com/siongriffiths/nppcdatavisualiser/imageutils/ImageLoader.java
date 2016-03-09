package com.siongriffiths.nppcdatavisualiser.imageutils;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//todo handle loading of duplicate information - i.e check idS FIRST? :(
    public static final Logger LOGGER = Logger.getLogger(ImageLoader.class);


    public void testing() {
        File dir = new File(imageRepoRoot);
        File[] directories = dir.listFiles();
        if (directories != null) {

            for (File plantDir : directories) {
                //create new plant with barcode = dirname
                Plant plant = new Plant(plantDir.getName());
                File[] files = plantDir.listFiles();

                if (files != null) {
                    File tempDir = new File(plantDir.getPath()+"/vis/sv/000-0-0-0/");
                    File[] plantImages = tempDir.listFiles();

                    if(plantImages!=null) {
                        for (File imageFile : plantImages) {

                            Date imageDate = extractDateFromImageName(imageFile.getName());
                            PlantDay plantDay = new PlantDay(imageDate);
                            plantDay.setPlant(plant);
                            plant.addPlantDay(plantDay);

                            //// TODO: 08/03/2016 -move to helper methods
                            PlantImage image = new PlantImage();
                            String path = convertFromRetardedWindowsFilePath(imageFile.getPath());
                            path = removeSurplusDirectoryGibberish(path);
                            image.setFilePath(path);
                            image.setPlantDay(plantDay);
                            plantDay.addPlantImage(image);
                        }
                    }
                }
                plantManager.savePlant(plant);
            }

        }
    }

    private String convertFromRetardedWindowsFilePath(String retardPath){
        return FilenameUtils.separatorsToUnix(retardPath);
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

//        LOGGER.info("name of file is  : " + trimmedName);
        return date;
    }
}

//                if (files != null) {
//                    for (File imagefile : files) {
//                        String imagePath = dir.getName() + "/" + subDir.getName() + "/" + imagefile.getName();
//                        PlantImage plantImage = new PlantImage(imagePath);
//                        plantImage.setPlantDay(plant);
//                        plant.addPlantImage(plantImage);
//                    }
//                }
