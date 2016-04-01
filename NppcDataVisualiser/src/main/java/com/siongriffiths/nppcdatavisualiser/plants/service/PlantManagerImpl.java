package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Service("plantManager")
public class PlantManagerImpl implements PlantManager {

    @Autowired
    private PlantDao plantDao;

    @Override
    public void savePlant(Plant plant) {
        plantDao.save(plant);
    }

    @Override
    public void deleteAllPlants() {
        plantDao.deleteAll();
    }

    @Override
    public List<Plant> getAllPlants() {
        return  plantDao.findAll();
    }

    @Override
    public Plant getPlantByBarcode(String barCode) {
       return plantDao.findByBarCode(barCode);
    }

    @Override
    public Plant createOrGetPlantByBarcode(String barCode) {
        Plant plant = getPlantByBarcode(barCode);

        if(plant == null){
            return createNewPlant(barCode);
        } else {
            return plant;
        }
    }


    @Override
    public void tagPlant(TagData tag, Plant plant) {
        plant.addTag(tag);
    }

    @Override
    public List<Plant> findPlantsByTag(TagData tagData) {
        return plantDao.findByTagData(tagData.getId());
    }

    @Override
    public List<Plant> findPlantsByExperimentCode(String experimentCode, Pageable pageable) {
        return plantDao.findByExperimentCode(experimentCode, pageable);
    }

    @Override
    public Plant getPlantByID(long id) {
        return plantDao.findOne(id);
    }

    @Override
    public Plant getPlantByID(String id) {
        long parsedId = Long.parseLong(id);
        return getPlantByID(parsedId);
    }


    private Plant createNewPlant(String barCode) {
        return new Plant(barCode);
    }

}
