package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The PlantDayManager interface defines the public business processes for managing PlantDay objects
 * and their interactions within the system. *
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
     * Retrieves a list of PlantDays from the persistence layer which have been associated with a specific TagData
     * @param tagData the target TagData
     * @return List of PlantDays associated with parameter TagData
     */
    List<PlantDay> findPlantDaysByTag(TagData tagData);

    /**
     * Retrieves a PlantDay from the persistence layer by its parent Plant and date
     * @param plant The parent Plant of the PlantDay
     * @param date The date
     * @return The PlantDay associated with the parameter Date and Plant
     */
    PlantDay findByPlantAndDate(Plant plant, Date date);

    /**
     * Given a Date, Plant object and a PlantImage object, this method will either create a new PlantDay with the
     * parameter data or retrieve the PlantDay corresponding with the Plant and Date and add the passed in PlantImage to that day
     * @param date Date object used to identify a PlantDay
     * @param plantImage PlantImage object that's to be added to a PlantDay
     * @param plant Parent Plant object of a PlantDay
     */
    void addToOrCreatePlantDay(Date date, PlantImage plantImage, Plant plant);

    /**
     * Method allows the addition of a String key-value attribute pair to be added to a PlantDay
     * @param day The PlantDay to add the attribute pair against
     * @param key Attribute key
     * @param value Attribute value
     */
    void addAttributeToDay(PlantDay day,String key, String value);

    List<PlantDay> getPlantDaysByPlant(Plant targetPlant, Pageable pageable);
}
