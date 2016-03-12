package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantDayTagInfo;
import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantForm;
import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/plants")
public class PlantPageController {

    private static final String PLANT_NOT_FOUND_PATH = "plants/notfound";
    private static final String PLANT_DETAIL_PATH = "plants/plantdetail";
    private static final String PLANT_DAY_TAG_FRAGMENT =  "plants/plantFragments :: tagFragment";
    private static final String PLANT_DAY_ATTRIB_FRAGMENT =  "plants/plantFragments :: attribFragment";
    private static final Logger LOGGER = Logger.getLogger(PlantPageController.class);

    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantImageManager plantImageManager;
    @Autowired
    private PlantDayManager plantDayManager;
    @Autowired
    private TagManager tagManager;

    @RequestMapping
    public String showPlants(Model model){
        model.addAttribute("plantList", plantManager.getAllPlants());
        model.addAttribute("plantForm" ,new PlantForm());
        return "plants/show";
    }

    //// TODO: 10/03/2016 sanitize inputs - look into using filter chains and sanitize eveything
    @RequestMapping(value = "{plantBarCode}",method = RequestMethod.GET)
    public String showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode){
        LOGGER.info(barCode);

        String viewPath;

        model.addAttribute("plantTagInfo" ,new PlantDayTagInfo());

        Plant targetPlant  = plantManager.getAndInitialisePlantByBarCode(barCode);
        if(targetPlant == null){
            model.addAttribute("barcode", barCode);
            viewPath = PLANT_NOT_FOUND_PATH;
        }else {
            model.addAttribute("plant", targetPlant);
            viewPath = PLANT_DETAIL_PATH;
        }
        return viewPath;
    }


    @RequestMapping(value = "/addTag/{plantDayId}/{attribName}/{attribValue}", method = RequestMethod.GET)
    public String tagPlant( Model model, @PathVariable("plantDayId") String plantDayID,
                            @PathVariable("attribName") String attribName,
                            @PathVariable("attribValue") String attribValue) {
        PlantDay day = plantDayManager.getPlantDayByID(Long.parseLong(plantDayID));
        Metadata plantDayData = day.getPlantDayMetaData();
        plantDayData.addDataAttribute(attribName,attribValue);
        plantDayManager.savePlantDay(day);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_ATTRIB_FRAGMENT;
    }


    @RequestMapping(value = "/tagged", method = RequestMethod.POST)
    public String tagPlant(@ModelAttribute PlantDayTagInfo plantDayTagInfo, Model model){
        model.addAttribute("plantTagInfo" ,new PlantDayTagInfo());
        String content = plantDayTagInfo.getTagContent();
        PlantDay day = plantDayManager.getPlantDayByID(Long.parseLong(plantDayTagInfo.getPlantDayID()));
        TagData tag = tagManager.createOrGetTag(content);
        plantDayManager.tagPlantDay(tag, day);
        tagManager.saveTagData(tag);
        plantDayManager.savePlantDay(day);
        plantDayManager.findPlantDaysByTag(tag);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_TAG_FRAGMENT;
    }
}
