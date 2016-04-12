package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 14/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("graphingManager")
public class GraphingManagerImpl implements GraphingManager {

    @Autowired
    private PlantManager plantManager;


    @Override
    public Map<String, List<String>> getGraphDataForPlant(Plant plant, String attrib1, String attrib2) {
        Map <String, List<String>> graphDataMap = new HashMap<>();

        List <String> attrib1List = new ArrayList<>();
        List <String> attrib2List = new ArrayList<>();

        if(plant != null) {
            for (PlantDay day : plant.getPlantDays()) {
                if (day.getMetadata().getDataAttributes().get(attrib1) != null) {
                    attrib1List.add(day.getDate().toString());
                    attrib2List.add(day.getMetadata().getDataAttributes().get(attrib1));
                }
            }
        }

        graphDataMap.put("x",attrib1List);
        graphDataMap.put("y",attrib2List);

        return graphDataMap;
    }

    @Override
    public Map<String, List<String>> getGraphData(String attribX, String attribY, String experimentCode) {

        Map <String, List<String>> graphDataMap = new HashMap<>();

        List <String> attrib1List = new ArrayList<>();
        List <String> attrib2List = new ArrayList<>();

        for(Plant plant : plantManager.findPlantsByExperimentCode(experimentCode)){
            String xValue = plant.getMetadata().getDataAttributes().get(attribX);
            String yValue = plant.getMetadata().getDataAttributes().get(attribY);

            if(xValue != null){
                attrib1List.add(xValue);
            }
            if(yValue != null){
                attrib2List.add((yValue));
            }
        }

        graphDataMap.put("x",attrib1List);
        graphDataMap.put("y",attrib2List);

        return graphDataMap;
    }


}
