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

    @Override
    public void savePlantDay(PlantDay plantDay) {
        plantDayDao.save(plantDay);
    }

    @Override
    public void tagPlantDay(TagData tag, PlantDay day) {
        day.addTag(tag);
    }

    @Override
    public PlantDay getPlantDayByID(long id) {
        return plantDayDao.findOne(id);
    }

    @Override
    public PlantDay getPlantDayByID(String id) {
        long parsedId = Long.parseLong(id);
        return getPlantDayByID(parsedId);
    }

    @Override
    public List<PlantDay> findPlantDaysByTag(TagData tagData) {
        return plantDayDao.findByTagData(tagData.getId());
    }

    @Override
    public List<PlantDay> findByTagDataForExperiment(TagData tagData, Experiment experiment) {
        return plantDayDao.findByTagDataForExperiment(tagData.getId(),experiment);
    }

    @Override
    public PlantDay findByPlantAndDate(Plant plant, Date date) {
        return plantDayDao.findByPlantAndDate(plant, date);
    }

    @Override
    public List<PlantDay> findPlantDaysByExperiment(Experiment experiment) {
        return plantDayDao.findByExperiment(experiment);
    }

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

    @Override
    public void addAttributeToDay(PlantDay day, String key, String value) {
        day.getMetadata().addDataAttribute(key, value);
        savePlantDay(day);
    }

    @Override
    public Page<PlantDay> getPlantDaysByPlant(Plant plant, Pageable pageable) {
        return plantDayDao.findByPlant(plant, pageable);
    }

    @Override
    public void resetTagsForExperiment(Experiment experiment) {
        for(PlantDay day : findPlantDaysWithTagsByExperiment(experiment)){
            day.setTags(new HashSet<TagData>());
            savePlantDay(day);
        }
    }

    @Override
    public List<PlantDay> findPlantDaysWithTagsByExperiment(Experiment experiment) {
        return plantDayDao.findPlantDaysWithTagsByExperiment(experiment);
    }


    private void associateImageToDay(PlantImage image, PlantDay day){
        day.addPlantImage(image);
        image.setPlantDay(day);
    }

    private void createPlantDay(Date date, Plant plant, PlantImage image){
        PlantDay plantDay = new PlantDay(date);
        associateImageToDay(image,plantDay);
        plant.addPlantDay(plantDay);
        plantDay.setPlant(plant);
    }

}
