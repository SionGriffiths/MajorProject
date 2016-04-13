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
 *
 * GraphingManagerImpl is a service class implementation of GraphingManager
 */
@Service("graphingManager")
public class GraphingManagerImpl implements GraphingManager {

    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;


    /**
     * Build a JSON like map structure from attributes available on a given plant to facilitate
     * front end view components to generate graphical visualisations
     * @param plant the Plant instance
     * @param attrib1 the first attribute
     * @param attrib2 the second attribute
     * @return a JSON like map structure containing data for graph creation
     */
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

    /**
     * Build a JSON like map structure from attributes available on a given experiment to facilitate
     * front end view components to generate graphical visualisations
     * @param attribX the first or x axis attribute
     * @param attribY the second or y axis attribute
     * @param experimentCode code to identify current experiemnt
     * @return a JSON like map structure containing data for graph creation
     */
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
