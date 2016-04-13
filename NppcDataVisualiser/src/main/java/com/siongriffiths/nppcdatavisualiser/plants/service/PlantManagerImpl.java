package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 * PlantManagerImpl implements all business logic and behaviour as defined in the PlantManager interface.
 */

@Service("plantManager")
public class PlantManagerImpl implements PlantManager {


    /**
     * The PlantDao repository used to provide persistence layer interactions for PlantDay objects
     */
    @Autowired
    private PlantDao plantDao;

    /**
     * Persists a Plant
     * @param plant the Plant object to be persisted
     */
    @Override
    public void savePlant(Plant plant) {
        plantDao.save(plant);
    }

    /**
     * Deletes all Plants associated with a particular Experiment
     * @param experiment the Experiment
     */
    @Override
    public void deleteByExperiment(Experiment experiment) {
        plantDao.deleteByExperiment(experiment);
    }


    /**
     * Finds the plant corresponding to a particular barcaode
     * @param barCode the barcode
     * @return the plant corresponding to the barcode
     */
    @Override
    public Plant getPlantByBarcode(String barCode) {
        return plantDao.findByBarCode(barCode);
    }

    /**
     * Finds a Plant associated with a particular barcode or create it if it doesn't exist
     * @param barCode the barcode
     * @return the found or created Plant
     */
    @Override
    public Plant createOrGetPlantByBarcode(String barCode) {
        Plant plant = getPlantByBarcode(barCode);

        if(plant == null){
            return createNewPlant(barCode);
        } else {
            return plant;
        }
    }

    /**
     * Associate a TagData instance to a Plant
     * @param tag the TagData
     * @param plant the Plant
     */
    @Override
    public void tagPlant(TagData tag, Plant plant) {
        plant.addTag(tag);
    }


    /**
     * Finds plants associated with a particular TagData and a particular experiment, using  a TagData instance
     * @param tagData the TagData
     * @param experiment the experiment
     * @return a List of plants associated with the TagData and the Experiment
     */
    @Override
    public List<Plant> findPlantsByTagForExperiment(TagData tagData, Experiment experiment) {
        return plantDao.findByTagDataForExperiment(tagData.getId(),experiment);
    }

    /**
     * Returns a pagination page of plants for a given Experiment and page settings
     * @param experimentCode code of the Experiment
     * @param pageable pagination page settings
     * @return a pagination page of plants using the current experiment and page settings
     */
    @Override
    public Page<Plant> findPlantsByExperimentCode(String experimentCode, Pageable pageable) {
        return plantDao.findByExperimentCode(experimentCode, pageable);
    }

    /**
     * Returns a list of all plants associated with an Experiment
     * @param experimentCode code of the Experiement
     * @return list of all plants associated with the Experiment
     */
    @Override
    public List<Plant> findPlantsByExperimentCode(String experimentCode) {
        return plantDao.findByExperimentCode(experimentCode);
    }

    /**
     * Gets the plant corresponding to a particular ID
     * @param id the ID
     * @return the corresponding plant
     */
    @Override
    public Plant getPlantByID(long id) {
        return plantDao.findOne(id);
    }

    /**
     * Gets the plant corresponding to a particular ID as a String
     * @param id the ID String
     * @return the corresponding plant
     */
    @Override
    public Plant getPlantByID(String id) {
        long parsedId = Long.parseLong(id);
        return getPlantByID(parsedId);
    }

    /**
     * Resets all tags associated with Plants in the given Experiment
     * @param experiment
     */
    @Override
    public void resetTagsForExperiment(Experiment experiment) {
        for(Plant p : findPlantsWithTagsByExperiment(experiment)){
            p.setTags(new HashSet<TagData>());
            savePlant(p);
        }
    }

    /**
     * Finds plants for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged plants
     */
    @Override
    public List<Plant> findPlantsWithTagsByExperiment(Experiment experiment) {
        return plantDao.findPlantsWithTagsByExperiment(experiment);
    }

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
    @Override
    public List<Plant> findByTwoAttrributeValues(String attrKey1, String attrVal1,String attrKey2, String attrVal2) {
        return plantDao.findByTwoAttrributeValues(attrKey1,attrVal1,attrKey2,attrVal2);
    }

    /**
     * Creates a new Plant instance with the given bar code
     * @param barCode the bar code
     * @return the new Plant instance
     */
    private Plant createNewPlant(String barCode) {
        return new Plant(barCode);
    }

}
