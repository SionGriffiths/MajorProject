package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantManager {

    void savePlant(Plant plant);

    void deleteAllPlants();

    List<Plant> getAllPlants();

    Plant getPlantByBarcode(String barCode);

    Plant createOrGetPlantByBarcode(String barCode);

    void tagPlant(TagData tag, Plant plant);

    List<Plant> findPlantsByTag(TagData tagData);

    List<Plant> findPlantsByExperimentCode(String experimentCode, Pageable pageable);

    Plant getPlantByID(long id);

    Plant getPlantByID(String id);

}
