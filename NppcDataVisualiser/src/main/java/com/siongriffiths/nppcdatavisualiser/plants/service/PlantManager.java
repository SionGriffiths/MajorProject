package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.data.domain.Page;
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

    void deleteByExperiment(Experiment experiment);

    List<Plant> getAllPlants();

    Plant getPlantByBarcode(String barCode);

    Plant createOrGetPlantByBarcode(String barCode);

    void tagPlant(TagData tag, Plant plant);

    List<Plant> findPlantsByTag(TagData tagData);

    List<Plant> findPlantsByTagForExperiment(TagData tagData, Experiment experiment);

    Page<Plant> findPlantsByExperimentCode(String experimentCode, Pageable pageable);

    List<Plant> findPlantsByExperimentCode(String experimentCode);

    List<Plant> findPlantsByExperiment(Experiment experiment);

    Plant getPlantByID(long id);

    Plant getPlantByID(String id);

    void resetTagsForExperiment(Experiment experiment);

    List<Plant> findPlantsWithTagsByExperiment(Experiment experiment);

    List<Plant> findByTwoAttrributeValues(String attrKey1, String attrVal1,String attrKey2, String attrVal2);
}
