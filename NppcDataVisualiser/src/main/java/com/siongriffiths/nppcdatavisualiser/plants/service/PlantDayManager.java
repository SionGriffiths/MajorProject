package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantDayManager {
    void savePlantDay(PlantDay plantDay);
    void tagPlantDay(TagData tag, PlantDay day);
    PlantDay getPlantDayByID(long id);
}
