package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantDayDao {
    void savePlantDay(PlantDay plantDay);
    PlantDay getPlantDayById(long Id);
}
