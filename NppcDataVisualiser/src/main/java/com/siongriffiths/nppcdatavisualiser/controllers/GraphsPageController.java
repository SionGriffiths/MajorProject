package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.data.controlobjects.GraphCreationInfo;
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
 *
 * GraphPageController is an MVC controller class which provides services
 * to the graph page view. All http requests to /graphs will be processed by this controller.
 */
@Controller
@RequestMapping("/graphs")
public class GraphsPageController extends DefaultController{

    /**
     * View paths used by this controller
     */
    private static final String GRAPHS_SHOW_PATH = "graphs/show";
    private static final String GRAPHS_BY_PLANT_PATH = "graphs/forPlant";
    private static final String PLANT_NOT_FOUND_PATH = "plants/notfound";

    /**
     * The PlantManager service, provides access to plant data and related methods.
     */
    @Autowired
    PlantManager plantManager;

    /**
     * The GraphingManager service, produces graph data.
     */
    @Autowired
    GraphingManager graphingManager;

    /**
     * Method provides the default graph page view for the controller level url path mapping of /graphs.
     * Prepares the page model object with a form control object and a list of possible attributes for graphing
     * @param model The page model object
     * @return default graph page view path as String
     */
    @RequestMapping
    public String showGraphsPage(Model model){

        model.addAttribute("graphCreationInfo", new GraphCreationInfo());
        model.addAttribute("availableAttribs", getAttributeListForAllPlants(plantManager.getAllPlants()));
        return GRAPHS_SHOW_PATH;
    }

    /**
     * Method returns a graph creation page for a specific plant. Allows visualisation of attributes
     * specific to a single plant. Adds attributes available for graphing to the page model object
     * @param model The page model object
     * @param barCode The barcode identifying the target plant
     * @return View path, path to not found if plant barcode not in system, path to individual plant graph page otherwise
     */
    @RequestMapping(value = "/byPlant/{plantBarCode}",method = RequestMethod.GET)
    public String showPlantDetailGraphs(Model model, @PathVariable("plantBarCode") String barCode) {
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

    /**
     * Produces graph data from the attributes contained within the parameter form control object.
     * Produces a Map which is marshaled to JSON by Spring prior to being handed to the view.
     * @param model  The page model object
     * @param graphInfo the form control object posted to the method
     * @return A Map containing graph data in a nested format representing a JSON object
     */
    @ResponseBody
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public Map<String,List<String>> getGraphData(Model model, GraphCreationInfo graphInfo){


        String attribX = graphInfo.getxAxisAttribute();
        String attribY = graphInfo.getyAxisAttribute();
        model.addAttribute("graphCreationInfo", new GraphCreationInfo());
        return graphingManager.getGraphData(attribX,attribY);
    }

    /**
     * Produces graph data for a given plant using the parameter attributes.
     * Produces a Map which is marshaled to JSON by Spring prior to being handed to the view.
     * @param barCode The barcode identifying the target plant
     * @param attrib1 First attribute used to produce a graph
     * @param attrib2 Second attribute used to produce a graph
     * @return A Map containing graph data in a nested format representing a JSON object
     */
    @ResponseBody
    @RequestMapping(value = "/byPlant/{plantBarCode}/fromData/{attrib1}/{attrib2}"
            ,method = RequestMethod.GET)
    public Map<String,List<String>> getGraphDataForPlant(@PathVariable("plantBarCode") String barCode,
                                                 @PathVariable("attrib1") String attrib1,
                                                 @PathVariable("attrib2") String attrib2){

        Plant targetPlant  = plantManager.getPlantByBarcode(barCode);

        return graphingManager.getGraphDataForPlant(targetPlant,attrib1,attrib2);
    }


    /**
     * Gets all available attribute keys for a given plant
     * @param plant The target plant object
     * @return a Set containing all attribute keys for the target plant
     */
    private Set<String> getDayAttributeListForPlant(Plant plant){
        Set<String> attribKeySet = new HashSet<>();
        for(PlantDay day : plant.getPlantDays()){
            attribKeySet.addAll(day.getPlantDayMetaData().getDataAttributes().keySet());
        }
        return attribKeySet;
    }

    /**
     * Gets all available Plant attribute keys as a Set
     * @param plantList a List of all Plants in the current experiment
     * @return a Set containing all attribute keys for the list of plants
     */
    private Set<String> getAttributeListForAllPlants(List<Plant> plantList){
        Set<String> attribKeySet = new HashSet<>();
        for(Plant plant : plantList){
            attribKeySet.addAll(plant.getPlantMetaData().getDataAttributes().keySet());
        }
        return attribKeySet;
    }

}
