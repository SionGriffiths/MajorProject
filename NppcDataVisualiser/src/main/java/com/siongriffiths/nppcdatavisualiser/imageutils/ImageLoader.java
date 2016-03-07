package com.siongriffiths.nppcdatavisualiser.imageutils;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Component //Component scanning will find this and treat class as bean
public class ImageLoader {

    @Autowired
    private PlantManager plantManager;

    public static final Logger LOGGER = Logger.getLogger(ImageLoader.class);
    private static final String DEFAULT_IMAGE_DIRECTORY = "src/main/resources/static/images";

    public void testing() {
        File dir = new File(DEFAULT_IMAGE_DIRECTORY);
        File[] directories = dir.listFiles();
        if (directories != null) {


            for (File subDir : directories) {
                //create new plant with barcode = dirname
                Plant plant = new Plant(subDir.getName());


                File[] files = subDir.listFiles();
                if (files != null) {
                    for (File imagefile : files) {
                        String imagePath = dir.getName() + "/" + subDir.getName() + "/" + imagefile.getName();
                        PlantImage plantImage = new PlantImage(imagePath);
                        plantImage.setPlant(plant);
                        plant.addPlantImage(plantImage);
                    }
                }
                plantManager.savePlant(plant);
            }

        }
    }

}
