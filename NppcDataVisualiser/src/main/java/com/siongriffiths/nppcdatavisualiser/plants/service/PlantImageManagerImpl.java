package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("plantImageManager")
public class PlantImageManagerImpl implements PlantImageManager {

    @Autowired
    PlantImageDao plantImageDao;

    @Override
    public void savePlantImage(PlantImage plantImage) {
        plantImageDao.savePlantImage(plantImage);
    }

    @Override
    public PlantImage getPlantImageByID(long id) {
       return plantImageDao.getPlantImageById(id);
    }




}
