package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantDetailsForm;
import com.siongriffiths.nppcdatavisualiser.controlobjects.PlantForm;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/plants")
public class PlantPageController {

    public static final Logger LOGGER = Logger.getLogger(PlantPageController.class);

    @Autowired
    private PlantManager plantManager;

    @RequestMapping
    public String showPlants(Model model){
        model.addAttribute("plantList", plantManager.getAllPlants());
        model.addAttribute("plantForm" ,new PlantForm());
        return "plants/show";
    }

    @RequestMapping("{plantBarCode}")
    public String showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode){
        LOGGER.info(barCode);
        model.addAttribute("plant", plantManager.getPlantByBarcode(barCode));
        model.addAttribute("plantDetailsForm" ,new PlantDetailsForm());
        return "plants/plantdetail";
    }

    @ResponseBody
    @RequestMapping(value = "/tagged", method = RequestMethod.POST)
    public void tagPlant(@ModelAttribute PlantDetailsForm plantDetailsForm, Model model){
        model.addAttribute("plantDetailsForm" ,plantDetailsForm);
        LOGGER.info("id = " +plantDetailsForm.getPlantImageID());
        LOGGER.info("content = " +plantDetailsForm.getTagContent());

    }


}
