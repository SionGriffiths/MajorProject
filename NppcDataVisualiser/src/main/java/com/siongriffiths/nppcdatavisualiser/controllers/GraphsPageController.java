package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.data.service.GraphingManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/graphs")
public class GraphsPageController extends DefaultController{

    private static final String GRAPHS_SHOW_PATH = "graphs/show";
    private static final String GRAPHS_BY_PLANT_PATH = "graphs/forPlant";
    private static final String PLANT_NOT_FOUND_PATH = "plants/notfound";

    @Autowired
    PlantManager plantManager;
    @Autowired
    GraphingManager graphingManager;

    @RequestMapping
    public String showGraphsPage(){
        return GRAPHS_SHOW_PATH;
    }

    @RequestMapping(value = "/byPlant/{plantBarCode}",method = RequestMethod.GET)
    public String showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode) {
        String viewPath;

        Plant targetPlant  = plantManager.getPlantByBarcode(barCode);

        if(targetPlant == null){
            model.addAttribute("barcode", barCode);
            viewPath = PLANT_NOT_FOUND_PATH;
        }else {

            model.addAttribute("attribs",getDayAttributeListForPlant(targetPlant));
            model.addAttribute("plant", targetPlant);
            viewPath = GRAPHS_BY_PLANT_PATH;
        }

        return viewPath;
    }



    private Set<String> getDayAttributeListForPlant(Plant plant){
        Set<String> attribKeySet = new HashSet<>();
        for(PlantDay day : plant.getPlantDays()){
            attribKeySet.addAll(day.getPlantDayMetaData().getDataAttributes().keySet());
        }
        return attribKeySet;
    }


    @ResponseBody
    @RequestMapping(value = "/byPlant/{plantBarCode}/fromData/{attrib1}/{attrib2}"
            ,method = RequestMethod.GET)
    public Map<String,List<String>> getGraphData(Model model,
                                                 @PathVariable("plantBarCode") String barCode,
                                                 @PathVariable("attrib1") String attrib1,
                                                 @PathVariable("attrib2") String attrib2){

        Plant targetPlant  = plantManager.getPlantByBarcode(barCode);

        return graphingManager.getGraphDataForPlant(targetPlant,attrib1,attrib2);
    }

}
