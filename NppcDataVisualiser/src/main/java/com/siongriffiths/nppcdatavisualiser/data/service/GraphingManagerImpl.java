package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
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



    //// TODO: 14/03/2016 If second attrib is "date" then just add all applicable day dates into the list
    @Override
    public Map<String, List<String>> getGraphDataForPlant(Plant plant, String attrib1, String attrib2) {
        Map <String, List<String>> graphDataMap = new HashMap<>();

        List <String> attrib1List = new ArrayList<>();
        List <String> attrib2List = new ArrayList<>();

        for(PlantDay day : plant.getPlantDays()){
            if(day.getPlantDayMetaData().getDataAttributes().get(attrib1) != null){
                attrib1List.add(day.getDate().toString());
                attrib2List.add(day.getPlantDayMetaData().getDataAttributes().get(attrib1));
            }
        }

        graphDataMap.put("x",attrib1List);
        graphDataMap.put("y",attrib2List);

        return graphDataMap;
    }
}
