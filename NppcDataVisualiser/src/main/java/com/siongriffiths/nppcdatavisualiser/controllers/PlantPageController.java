package com.siongriffiths.nppcdatavisualiser.controllers;


import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantDayAttributeInfo;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantDayTagInfo;
import com.siongriffiths.nppcdatavisualiser.plants.controlobjects.PlantTagInfo;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/plants")
public class PlantPageController extends DefaultController {


    private static final String PLANTS_SHOW_PATH = "plants/show";
    private static final String PLANT_NOT_FOUND_PATH = "plants/notfound";
    private static final String PLANT_DETAIL_PATH = "plants/plantdetail";
    private static final String PLANT_DAY_TAG_FRAGMENT =  "plants/plantFragments :: dayTagFragment";
    private static final String PLANT_DAY_ATTRIB_FRAGMENT =  "plants/plantFragments :: dayAttribFragment";
    private static final String PLANT_ATTRIB_FRAGMENT =  "plants/plantFragments :: plantAttribFragment";
    private static final String PLANT_TAG_FRAGMENT =  "plants/plantFragments :: plantTagFragment";

    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantImageManager plantImageManager;
    @Autowired
    private PlantDayManager plantDayManager;
    @Autowired
    private TagManager tagManager;

    @RequestMapping
    public String showPlants(Model model, HttpSession session){
        //// TODO: 29/03/2016 NULLCHECK BROTHER
        String experimentCode = (String)session.getAttribute("experimentCode");
        model.addAttribute("plantList", plantManager.findPlantsByExperimentCode(experimentCode));
        model.addAttribute("plantTagInfo" ,new PlantTagInfo());
        return PLANTS_SHOW_PATH;
    }

    @RequestMapping(value = "{plantBarCode}",method = RequestMethod.GET)
    public String showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode){
        logger.info(barCode);

        String viewPath;

        model.addAttribute("plantTagInfo" ,new PlantDayTagInfo());
        model.addAttribute("plantDayAttributeInfo", new PlantDayAttributeInfo());

        Plant targetPlant  = plantManager.getPlantByBarcode(barCode);

        if(targetPlant == null){
            model.addAttribute("barcode", barCode);
            viewPath = PLANT_NOT_FOUND_PATH;
        }else {
            model.addAttribute("plant", targetPlant);
            viewPath = PLANT_DETAIL_PATH;
        }

        return viewPath;
    }


    @RequestMapping(value = "/addDayAttribute", method = RequestMethod.POST)
    public String addAttribute(@ModelAttribute PlantDayAttributeInfo plantDayAttributeInfo, Model model) {
        model.addAttribute("plantDayAttributeInfo", new PlantDayAttributeInfo());
        PlantDay day = plantDayManager.getPlantDayByID(plantDayAttributeInfo.getPlantDayID());
        Metadata plantDayData = day.getPlantDayMetaData();
        plantDayData.addDataAttribute(plantDayAttributeInfo.getAttribName(),plantDayAttributeInfo.getAttribVal());
        plantDayManager.savePlantDay(day);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_ATTRIB_FRAGMENT;
    }


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
