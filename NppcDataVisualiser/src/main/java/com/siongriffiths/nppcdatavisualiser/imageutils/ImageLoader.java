package com.siongriffiths.nppcdatavisualiser.imageutils;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantImageDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;


/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class ImageLoader {

    @Autowired
    PlantDao plantDao;
    @Autowired
    PlantImageDao plantImageDao;


    public static final Logger LOGGER = Logger.getLogger(ImageLoader.class);
    private static final String DEFAULT_IMAGE_DIRECTORY = "src/main/resources/static/images";

    public void testing() {
        File dir = new File(DEFAULT_IMAGE_DIRECTORY);
        File[] directories = dir.listFiles();
        if(directories != null){


            for(File subDir : directories){
                //create new plant with barcode = dirname
                Plant plant = new Plant(subDir.getName());


                File[] files = subDir.listFiles();
                if(files != null){
                    for(File imagefile : files){
                        LOGGER.info("get path = " + imagefile.getName() + "\n"
                                    + "get can path = " + subDir.getName() + "\n"
                                    + "get abs path = " + dir.getName());
                        String imagePath = dir.getName() + "/" + subDir.getName() + "/" + imagefile.getName();
                        PlantImage plantImage = new PlantImage(imagePath);
                        plantImageDao.savePlantImage(plantImage);
                        plant.addPlantImage(plantImage);
                    }
                }
                plantDao.savePlant(plant);
                //loop again then :
                //create new plantImage with path = subDir
            }
        }else{
            //exit gracefully / send info to frontend that no images or wrong dir
        }

    }

}
