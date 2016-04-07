package com.siongriffiths.nppcdatavisualiser.controllers;


import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantAttributeInfo;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantDayAttributeInfo;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantDayTagInfo;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantTagInfo;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantPageController is an MVC controller that processes all requests to the root url /plants
 * PlantPageController provides all views associated with the plants in the system including individual
 * plant detail pages
 */
@Controller
@RequestMapping("/plants")
public class PlantPageController extends DefaultController {


    /**
     * View paths used by this controller
     */
    private static final String PLANTS_SHOW_PATH = "plants/show";
    private static final String PLANT_NOT_FOUND_PATH = "plants/notfound";
    private static final String PLANT_DETAIL_PATH = "plants/plantdetail";
    private static final String PLANT_DAY_TAG_FRAGMENT =  "plants/plantFragments :: dayTagFragment";
    private static final String PLANT_DAY_ATTRIB_FRAGMENT =  "plants/plantFragments :: dayAttribFragment";
    private static final String PLANT_ATTRIB_FRAGMENT =  "plants/plantFragments :: plantAttribFragment";
    private static final String PLANT_TAG_FRAGMENT =  "plants/plantFragments :: plantTagFragment";

    /**
     * Pagination constants
     */
    private static final int DEFAULT_PAGINATION_START_PAGE = 1;
    private static final int DEFAULT_PAGINATION_RESULTS = 10;

    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;

    /**
     * PlantDayManager, a service class providing access to business logic and persistence for PlantDay objects
     */
    @Autowired
    private PlantDayManager plantDayManager;

    /**
     * TagManager, a service class providing access to business logic and persistence for TagData objects
     */
    @Autowired
    private TagManager tagManager;

    /**
     * Shows the default plant page. Displays a list of all plants in the currently selected experiment
     * @param model the page model object
     * @param session the HttpSession object containing an experiment code
     * @return view path for the default plants page
     */
    @RequestMapping
    public String show(Model model, HttpSession session){
        return showPlants(model,session, DEFAULT_PAGINATION_START_PAGE, DEFAULT_PAGINATION_RESULTS);
    }

    @RequestMapping(params = {"page"}, method = RequestMethod.GET)
    public String showForPage(@RequestParam( "page" ) int page,
                              Model model, HttpSession session){
        return showPlants(model,session,page,DEFAULT_PAGINATION_RESULTS );
    }

    @RequestMapping(params = {"page", "size" }, method = RequestMethod.GET)
    public String showForPageAndSize(@RequestParam( "page" ) int page, @RequestParam( "size" ) int size,
                                     Model model, HttpSession session){
        return showPlants(model,session,page,size );
    }

    private String showPlants(Model model, HttpSession session, int pageNum, int numPerPage){
        String experimentCode = (String)session.getAttribute("experimentCode");

        model.addAttribute("plantAttributeInfo", new PlantAttributeInfo());

        if(pageNum < 1){
            pageNum = 1;
        }
        if(numPerPage < 1){
            numPerPage = 1;
        }

        pageNum -= 1; //pages are zero indexed in spring, front end pages should be indexed from 1


        Page<Plant> page = plantManager.findPlantsByExperimentCode(experimentCode, new PageRequest(pageNum,numPerPage));

        int currentPageIndex = page.getNumber()+1;

        model.addAttribute("currentPage", currentPageIndex);
        model.addAttribute("currentSize", numPerPage);
        model.addAttribute("nextPage", currentPageIndex+1);
        model.addAttribute("prevPage", currentPageIndex-1);
        model.addAttribute("lastPage", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("plantList", page );
        model.addAttribute("plantTagInfo" ,new PlantTagInfo());

        return PLANTS_SHOW_PATH;
    }

    @RequestMapping(value = "{plantBarCode}",method = RequestMethod.GET)
    public String showPlantDetailForPage(Model model, @PathVariable("plantBarCode") String barCode){

        return showPlantDetail(model,barCode,DEFAULT_PAGINATION_START_PAGE, DEFAULT_PAGINATION_RESULTS);

    }

    @RequestMapping(value = "{plantBarCode}", params = {"page"}, method = RequestMethod.GET)
    public String showPlantDetailForPage(Model model, @PathVariable("plantBarCode") String barCode,
                                         @RequestParam( "page" ) int page){

        return showPlantDetail(model,barCode,page,DEFAULT_PAGINATION_RESULTS);

    }

    @RequestMapping(value = "{plantBarCode}", params = {"page", "size"}, method = RequestMethod.GET)
    public String showPlantDetailForPageAndSize(Model model, @PathVariable("plantBarCode") String barCode,
                                                @RequestParam( "page" ) int page,
                                                @RequestParam( "size" ) int size){

        return showPlantDetail(model,barCode,page,size);

    }

    /**
     * Shows a page detailing an individual plant and associated time serried data and images
     * @param model the page model object
     * @param barCode the barcode identifying a plant
     * @return view path for the plants detail page
     */

    private String showPlantDetail(Model model,  String barCode, int pageNum, int numPerPage){
        logger.info(barCode);

        String viewPath;
        Plant targetPlant  = plantManager.getPlantByBarcode(barCode);


        if(targetPlant == null){
            model.addAttribute("barcode", barCode);
            viewPath = PLANT_NOT_FOUND_PATH;
        }else {

            if(pageNum < 1){
                pageNum = 1;
            }
            if(numPerPage < 1){
                numPerPage = 1;
            }

            pageNum -= 1; //pages are zero indexed in spring, front end pages should be indexed from 1


            model.addAttribute("plantTagInfo" ,new PlantDayTagInfo());
            model.addAttribute("plantDayAttributeInfo", new PlantDayAttributeInfo());


            Page<PlantDay> page = plantDayManager.getPlantDaysByPlant(targetPlant, new PageRequest(pageNum,numPerPage));
            int currentPageIndex = page.getNumber()+1;

            model.addAttribute("currentPage", currentPageIndex);
            model.addAttribute("currentSize", numPerPage);
            model.addAttribute("nextPage", currentPageIndex+1);
            model.addAttribute("prevPage", currentPageIndex-1);
            model.addAttribute("lastPage", page.getTotalPages());
            model.addAttribute("totalElements", page.getTotalElements());

            model.addAttribute("plant", targetPlant);
            model.addAttribute("dayList", page);
            viewPath = PLANT_DETAIL_PATH;
        }

        return viewPath;
    }

    /**
     * Adds a key value attribute pair to a PlantDay and returns the page fragment containing the updated attributes
     * @param plantDayAttributeInfo form backing control object representing a form in the front end
     * @param model the page model object
     * @return view fragment path for the attributes section in the plant detail page
     */
    @RequestMapping(value = "/addDayAttribute", method = RequestMethod.POST)
    public String addDayAttribute(@ModelAttribute PlantDayAttributeInfo plantDayAttributeInfo, Model model) {
        model.addAttribute("plantDayAttributeInfo", new PlantDayAttributeInfo());
        PlantDay day = plantDayManager.getPlantDayByID(plantDayAttributeInfo.getPlantDayID());
        Metadata plantDayData = day.getMetadata();
        plantDayData.addDataAttribute(plantDayAttributeInfo.getAttribName(),plantDayAttributeInfo.getAttribVal());
        plantDayManager.savePlantDay(day);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_ATTRIB_FRAGMENT;
    }

    @RequestMapping(value = "/addPlantAttribute", method = RequestMethod.POST)
    public String addPlantAttribute(@ModelAttribute PlantAttributeInfo plantAttributeInfo, Model model) {
        model.addAttribute("plantAttributeInfo", new PlantAttributeInfo());
        Plant plant = plantManager.getPlantByID(plantAttributeInfo.getPlantID());
        Metadata plantData = plant.getMetadata();
        plantData.addDataAttribute(plantAttributeInfo.getAttribName(),plantAttributeInfo.getAttribVal());
        plantManager.savePlant(plant);
        model.addAttribute("plant", plant);
        return PLANT_ATTRIB_FRAGMENT;
    }


    /**
     * Add a tag to a PlantDay
     * @param plantDayTagInfo form backing control object representing a form in the front end
     * @param model the page model object
     * @return view fragment path for the tags section in the plant detail page
     */
    @RequestMapping(value = "/addDayTag", method = RequestMethod.POST)
    public String tagPlantDay(@ModelAttribute PlantDayTagInfo plantDayTagInfo, Model model){
        model.addAttribute("plantTagInfo" ,new PlantDayTagInfo());
        String content = plantDayTagInfo.getTagContent();
        PlantDay day = plantDayManager.getPlantDayByID(plantDayTagInfo.getPlantDayID());
        TagData tag = tagManager.createOrGetTag(content);
        plantDayManager.tagPlantDay(tag, day);
        tagManager.saveTagData(tag);
        plantDayManager.savePlantDay(day);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_TAG_FRAGMENT;
    }

    /**
     * Adds a key value attribute pair to a Plant and returns the page fragment containing the updated attributes
     * @param plantTagInfo form backing control object representing a form in the front end
     * @param model the page model object
     * @return view fragment path for the attributes section in the plant  page
     */
    @RequestMapping(value = "/addPlantTag", method = RequestMethod.POST)
    public String tagPlantDay(@ModelAttribute PlantTagInfo plantTagInfo, Model model){
        model.addAttribute("plantTagInfo" ,new PlantTagInfo());
        String content = plantTagInfo.getTagContent();
        Plant plant = plantManager.getPlantByID(plantTagInfo.getPlantID());
        TagData tag = tagManager.createOrGetTag(content);
        plantManager.tagPlant(tag, plant);
        tagManager.saveTagData(tag);
        plantManager.savePlant(plant);
        model.addAttribute("plant", plant);
        return PLANT_TAG_FRAGMENT;
    }


}
