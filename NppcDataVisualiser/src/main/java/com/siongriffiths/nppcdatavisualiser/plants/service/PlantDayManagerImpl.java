package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantDayManagerImpl implements all business logic and behaviour as defined in the PlantDayManager interface.
 * This class uses the @Service spring annotation and is autowired into subsequent classes in the system using
 * the plantDayManager identifier.
 */
@Service("plantDayManager")
public class PlantDayManagerImpl implements PlantDayManager {

    /**
     * The PlantDayDao repository used to provide persistence layer interactions for PlantDay objects
     */
    @Autowired
    private PlantDayDao plantDayDao;

    /**
     * Persists a PlantDay
     * @param plantDay the PlantDay object to be persisted
     */
    @Override
    public void savePlantDay(PlantDay plantDay) {
        plantDayDao.save(plantDay);
    }

    /**
     * Associates TagData to a given PlantDay
     * @param tag The TagData to be associated
     * @param day The PlantDay with which the data is to be associated with
     */
    @Override
    public void tagPlantDay(TagData tag, PlantDay day) {
        day.addTag(tag);
    }

    /**
     * Retrieves a PlantDay from the persistence layer via ID
     * @param id The ID of the PlantDay to be retrieved
     * @return The PlantDay corresponding with the ID
     */
    @Override
    public PlantDay getPlantDayByID(long id) {
        return plantDayDao.findOne(id);
    }

    /**
     * Retrieves a PlantDay from the persistence layer via ID
     * @param id The String representation of the ID of the PlantDay to be retrieved
     * @return The PlantDay corresponding with the ID
     */
    @Override
    public PlantDay getPlantDayByID(String id) {
        long parsedId = Long.parseLong(id);
        return getPlantDayByID(parsedId);
    }

    /**
     * Retrieves a list of PlantDays from the persistence layer which have been associated with a specific TagData
     * @param tagData the target TagData
     * @return List of PlantDays associated with parameter TagData
     */
    @Override
    public List<PlantDay> findPlantDaysByTag(TagData tagData) {
        return plantDayDao.findByTagData(tagData.getId());
    }

    /**
     * Finds PlantDays associated with a particular TagData and a particular experiment, using a TagData instance
     * @param tagData the TagData
     * @param experiment the experiment
     * @return a List of PlantDays associated with the TagData and the Experiement
     */
    @Override
    public List<PlantDay> findByTagDataForExperiment(TagData tagData, Experiment experiment) {
        return plantDayDao.findByTagDataForExperiment(tagData.getId(),experiment);
    }

    /**
     * Retrieves a PlantDay from the persistence layer by its parent Plant and date
     * @param plant The parent Plant of the PlantDay
     * @param date The date
     * @return The PlantDay associated with the parameter Date and Plant
     */
    @Override
    public PlantDay findByPlantAndDate(Plant plant, Date date) {
        return plantDayDao.findByPlantAndDate(plant, date);
    }

    /**
     * Finds PlantDays associated with an Experiment
     * @param experiment the Experiment
     * @return a list of PlantDays associated with the Experiment
     */
    @Override
    public List<PlantDay> findPlantDaysByExperiment(Experiment experiment) {
        return plantDayDao.findByExperiment(experiment);
    }

    /**
     * Given a Date, Plant object and a PlantImage object, this method will either create a new PlantDay with the
     * parameter data or retrieve the PlantDay corresponding with the Plant and Date and add the passed in PlantImage to that day
     * @param date Date object used to identify a PlantDay
     * @param plantImage PlantImage object that's to be added to a PlantDay
     * @param plant Parent Plant object of a PlantDay
     */
    @Override
    public void addToOrCreatePlantDay(Date date, PlantImage plantImage, Plant plant){

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

    /**
     * Method allows the addition of a String key-value attribute pair to be added to a PlantDay
     * @param day The PlantDay to add the attribute pair against
     * @param key Attribute key
     * @param value Attribute value
     */
    @Override
    public void addAttributeToDay(PlantDay day, String key, String value) {
        day.getMetadata().addDataAttribute(key, value);
        savePlantDay(day);
    }

    /**
     * Returns a pagination page of plants for a given plant and page settings
     * @param plant the plant
     * @param pageable pagination page settings
     * @return a pagination page of PlantDays using the current plant and page settings
     */
    @Override
    public Page<PlantDay> getPlantDaysByPlant(Plant plant, Pageable pageable) {
        return plantDayDao.findByPlant(plant, pageable);
    }

    /**
     * Resets all tags associated with PlantDays in the given Experiment
     * @param experiment
     */
    @Override
    public void resetTagsForExperiment(Experiment experiment) {
        for(PlantDay day : findPlantDaysWithTagsByExperiment(experiment)){
            day.setTags(new HashSet<TagData>());
            savePlantDay(day);
        }
    }

    /**
     * Finds PlantDays for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged PlantDays
     */
    @Override
    public List<PlantDay> findPlantDaysWithTagsByExperiment(Experiment experiment) {
        return plantDayDao.findPlantDaysWithTagsByExperiment(experiment);
    }


    /**
     * Adds a PlantImage instance to the current PlantDay
     * @param image the PlantImage
     * @param day the PlantDay
     */
    private void associateImageToDay(PlantImage image, PlantDay day){
        day.addPlantImage(image);
    }

    /**
     * Creates a PlantDay with associations to a Plant and PlantImage instances
     * @param date the Date for the PlantDay
     * @param plant the Plant
     * @param image the PlantImage
     */
    private void createPlantDay(Date date, Plant plant, PlantImage image){
        PlantDay plantDay = new PlantDay(date);
        associateImageToDay(image,plantDay);
        plant.addPlantDay(plantDay);
        plantDay.setPlant(plant);
    }

}
