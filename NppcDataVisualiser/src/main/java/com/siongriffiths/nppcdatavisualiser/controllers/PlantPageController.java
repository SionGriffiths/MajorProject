package com.siongriffiths.nppcdatavisualiser.controllers;

        import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantDetailsForm;
        import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantForm;
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
        import org.springframework.web.bind.annotation.*;

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
    private static final String PLANT_DAY_TAG_FRAGMENT =  "plants/tagFragment :: tagFragment";
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
    @RequestMapping(value = "{plantBarCode}")
    public String showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode){
        LOGGER.info(barCode);

        String viewPath;

        Plant targetPlant  = plantManager.getAndInitialisePlantByBarCode(barCode);
        if(targetPlant == null){
            model.addAttribute("barcode", barCode);
            viewPath = PLANT_NOT_FOUND_PATH;
        }else {
//        plantManager.initializePlantObject(targetPlant);
            model.addAttribute("plant", targetPlant);
//            model.addAttribute("plantDetailsForm", new PlantDetailsForm());
            viewPath = PLANT_DETAIL_PATH;
        }
        return viewPath;
    }


    @RequestMapping(value = "/addTag/{plantDayId}/{tagContent}")
    public String tagPlant( Model model, @PathVariable("plantDayId") String plantDayID,
                            @PathVariable("tagContent") String tagContent){
        PlantDay day = plantDayManager.getPlantDayByID(Long.parseLong(plantDayID));
        TagData tag = tagManager.createOrGetTag(tagContent);
        plantDayManager.tagPlantDay(tag, day);
        tagManager.saveTagData(tag);
        plantDayManager.savePlantDay(day);
        model.addAttribute("plantDay", day);
        return PLANT_DAY_TAG_FRAGMENT;
    }

}
