package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantDao {

    void savePlant(Plant plant);
    List<Plant> getAllPlants();
    Plant getPlantByBarcode(String barcode);
}