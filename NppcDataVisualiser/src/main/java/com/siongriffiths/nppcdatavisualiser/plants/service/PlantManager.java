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
 * The PlantManager interface defines the public business processes for managing Plant objects
 * and their interactions within the system.
 */
public interface PlantManager {

    /**
     * Persists a Plant
     * @param plant the Plant object to be persisted
     */
    void savePlant(Plant plant);

    /**
     * Deletes all Plants associated with a particular Experiment
     * @param experiment the Experiment
     */
    void deleteByExperiment(Experiment experiment);

    /**
     * Finds the plant corresponding to a particular barcaode
     * @param barCode the barcode
     * @return the plant corresponding to the barcode
     */
    Plant getPlantByBarcode(String barCode);

    /**
     * Finds a Plant associated with a particular barcode or create it if it doesn't exist
     * @param barCode the barcode
     * @return the found or created Plant
     */
    Plant createOrGetPlantByBarcode(String barCode);

    /**
     * Associate a TagData instance to a Plant
     * @param tag the TagData
     * @param plant the Plant
     */
    void tagPlant(TagData tag, Plant plant);

    /**
     * Finds plants associated with a particular TagData and a particular experiment, using  a TagData instance
     * @param tagData the TagData
     * @param experiment the experiment
     * @return a List of plants associated with the TagData and the Experiment
     */
    List<Plant> findPlantsByTagForExperiment(TagData tagData, Experiment experiment);

    /**
     * Returns a pagination page of plants for a given Experiment and page settings
     * @param experimentCode code of the Experiment
     * @param pageable pagination page settings
     * @return a pagination page of plants using the current experiment and page settings
     */
    Page<Plant> findPlantsByExperimentCode(String experimentCode, Pageable pageable);


    /**
     * Returns a list of all plants associated with an Experiment
     * @param experimentCode code of the Experiement
     * @return list of all plants associated with the Experiment
     */
    List<Plant> findPlantsByExperimentCode(String experimentCode);

    /**
     * Gets the plant corresponding to a particular ID
     * @param id the ID
     * @return the corresponding plant
     */
    Plant getPlantByID(long id);

    /**
     * Gets the plant corresponding to a particular ID as a String
     * @param id the ID String
     * @return the corresponding plant
     */
    Plant getPlantByID(String id);

    /**
     * Resets all tags associated with Plants in the given Experiment
     * @param experiment
     */
    void resetTagsForExperiment(Experiment experiment);

    /**
     * Finds plants for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged plants
     */
    List<Plant> findPlantsWithTagsByExperiment(Experiment experiment);

    /**
     * Finds plants based on two key value pairs within the plants metadata map. This method is used for finding plants
     * corresponding to data points in visualisations in the graph view. Each key/value pair represents an axis label and
     * value for the data point.
     * @param attrKey1 key for the first pair
     * @param attrVal1 value for the first pair
     * @param attrKey2 key for the second pair
     * @param attrVal2 value for the second pair
     * @return a list of all plants matching the given key value pairs
     */
    List<Plant> findByTwoAttrributeValues(String attrKey1, String attrVal1,String attrKey2, String attrVal2);
}
