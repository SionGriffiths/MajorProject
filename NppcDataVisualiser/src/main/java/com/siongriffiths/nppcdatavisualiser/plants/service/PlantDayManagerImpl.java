package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("plantDayManager")
public class PlantDayManagerImpl implements PlantDayManager {

    @Autowired
    private PlantDayDao plantDayDao;

    @Override
    public void savePlantDay(PlantDay plantDay) {
        plantDayDao.savePlantDay(plantDay);
    }

    @Override
    public void tagPlantDay(TagData tag, PlantDay day) {
        day.addTag(tag);
    }

    @Override
    public PlantDay getPlantDayByID(long id) {
        return plantDayDao.getPlantDayById(id);
    }


}
