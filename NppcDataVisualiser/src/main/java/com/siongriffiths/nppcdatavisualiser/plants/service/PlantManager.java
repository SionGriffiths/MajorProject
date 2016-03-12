package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantManager {

    void savePlant(Plant plant);

    List<Plant> getAllPlants();

    Plant getPlantByBarcode(String barCode);

    Plant getAndInitialisePlantByBarCode(String barCode);

    void initializePlantObject(Plant plant);
}
