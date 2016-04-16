package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The PlantDayManager interface defines the public business processes for managing PlantDay objects
 * and their interactions within the system.
 */
public interface PlantDayManager {

    /**
     * Persists a PlantDay
     * @param plantDay the PlantDay object to be persisted
     */
    void savePlantDay(PlantDay plantDay);

    /**
     * Associates TagData to a given PlantDay
     * @param tag The TagData to be associated
     * @param day The PlantDay with which the data is to be associated with
     */
    void tagPlantDay(TagData tag, PlantDay day);

    /**
     * Retrieves a PlantDay from the persistence layer via ID
     * @param id The ID of the PlantDay to be retrieved
     * @return The PlantDay corresponding with the ID
     */
    PlantDay getPlantDayByID(long id);

    /**
     * Retrieves a PlantDay from the persistence layer via ID
     * @param id The String representation of the ID of the PlantDay to be retrieved
     * @return The PlantDay corresponding with the ID
     */
    PlantDay getPlantDayByID(String id);


    /**
     * Finds PlantDays associated with a particular TagData and a particular experiment, using a TagData instance
     * @param tagData the TagData
     * @param experiment the experiment
     * @return a List of PlantDays associated with the TagData and the Experiement
     */
    List<PlantDay> findByTagDataForExperiment(TagData tagData, Experiment experiment);

    /**
     * Retrieves a PlantDay from the persistence layer by its parent Plant and date
     * @param plant The parent Plant of the PlantDay
     * @param date The date
     * @return The PlantDay associated with the parameter Date and Plant
     */
    PlantDay findByPlantAndDate(Plant plant, Date date);


    /**
     * Finds PlantDays associated with an Experiment
     * @param experiment the Experiment
     * @return a list of PlantDays associated with the Experiment
     */
    List<PlantDay> findPlantDaysByExperiment(Experiment experiment);

    /**
     * Given a Date, Plant object and a PlantImage object, this method will either create a new PlantDay with the
     * parameter data or retrieve the PlantDay corresponding with the Plant and Date and add the passed in PlantImage to that day
     * @param date Date object used to identify a PlantDay
     * @param plantImage PlantImage object that's to be added to a PlantDay
     * @param plant Parent Plant object of a PlantDay
     */
    void addToOrCreatePlantDay(Date date, PlantImage plantImage, Plant plant);


    /**
     * Returns a pagination page of plants for a given plant and page settings
     * @param targetPlant the plant
     * @param pageable pagination page settings
     * @return a pagination page of PlantDays using the current plant and page settings
     */
    Page<PlantDay> getPlantDaysByPlant(Plant targetPlant, Pageable pageable);

    /**
     * Finds PlantDays for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged PlantDays
     */
    List<PlantDay> findPlantDaysWithTagsByExperiment(Experiment experiment);

    /**
     * Resets all tags associated with PlantDays in the given Experiment
     * @param experiment the experiment
     */
    void resetTagsForExperiment(Experiment experiment);
}
