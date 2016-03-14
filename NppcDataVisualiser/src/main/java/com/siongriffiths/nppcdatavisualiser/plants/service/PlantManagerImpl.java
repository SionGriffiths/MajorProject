package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
    PlantDao plantDao;

    @Override
    public void savePlant(Plant plant) {
        plantDao.save(plant);
    }

    @Override
    public List<Plant> getAllPlants() {
        return  plantDao.findAll();
    }

    @Override
    public Plant getPlantByBarcode(String barCode) {
       return plantDao.findByBarCode(barCode);
    }


}
