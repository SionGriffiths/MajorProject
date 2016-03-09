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
import java.util.Collections;
import java.util.Date;
import java.util.List;

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


    public void initPlantImages() {
        File dir = new File(imageRepoRoot);
        File[] directories = dir.listFiles();
        if (directories != null) {

            for (File plantDir : directories) {
                //create new plant with barcode = dirname
                Plant plant = new Plant(plantDir.getName());

                File[] cameraTypeFiles = plantDir.listFiles();

//                maybe make recursive attempt from here

                if (cameraTypeFiles != null) {
                    for(File cameraTypeFile : cameraTypeFiles){
                        String cameraType = cameraTypeFile.getName();
                        LOGGER.debug("Image type : " + cameraType);

                        if(cameraType.equalsIgnoreCase("nir")){
                            continue;
                        }

                        File[] viewTypeFiles = cameraTypeFile.listFiles();
                        if(viewTypeFiles != null){
                            for(File viewTypeFile : viewTypeFiles){
                                String viewType = viewTypeFile.getName();
                                LOGGER.debug("View type : " + viewType);

                                File[] angleOptionFiles = viewTypeFile.listFiles();
                                if(angleOptionFiles != null){
                                    for(File angleOptionFile : angleOptionFiles){
                                        String angleOption = angleOptionFile.getName();
                                        LOGGER.debug("Angle option : " + angleOption);

                                        File[] plantImageFiles = angleOptionFile.listFiles();
                                        if(plantImageFiles != null){
                                            for(File plantImageFile : plantImageFiles){
                                                String path = normaliseFilePath(plantImageFile.getPath());
                                                path = removeSurplusDirectoryGibberish(path);
                                                LOGGER.debug(path);
                                                Date date = extractDateFromImageName(plantImageFile.getName());
                                                PlantImage plantImage = new PlantImage();
                                                plantImage.setFilePath(path);
                                                addToOrCreatePlantDay(date,plantImage,plant);
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
            }
        }
    }

    private void addToOrCreatePlantDay(Date date, PlantImage plantImage, Plant plant){
        List<PlantDay> dayList = plant.getPlantDays();
        boolean dayFound = false;

        if(dayList.size() == 0){
            createPlantDay(date,plant,plantImage);
        } else {
            for (PlantDay day : dayList) {
                if (day.getDate().compareTo(date) == 0) {
                    associateImageToDay(plantImage, day);
                    dayFound = true;
                    break;
                }
            }
            if(!dayFound){
                createPlantDay(date,plant,plantImage);
            }
        }
    }

    private void associateImageToDay(PlantImage image, PlantDay day){
        day.addPlantImage(image);
        image.setPlantDay(day);
    }

    private void createPlantDay(Date date, Plant plant, PlantImage image){
        PlantDay plantDay = new PlantDay(date);
        plantDay.setPlant(plant);
        associateImageToDay(image,plantDay);
        plant.addPlantDay(plantDay);
    }

    private String normaliseFilePath(String retardPath){
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
        return date;
    }
}