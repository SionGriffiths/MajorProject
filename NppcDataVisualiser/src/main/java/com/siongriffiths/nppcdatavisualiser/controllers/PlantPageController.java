package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    // TODO: 28/02/2016 - Maybe just query the barcodes here and add them to page rather than the plant objects
    public ModelAndView showPlants(Model model){
        model.addAttribute("plantList", plantManager.getAllPlants());
        return new ModelAndView("plants/show");
    }

    @RequestMapping("{plantBarCode}")
    public ModelAndView showPlantDetail(Model model, @PathVariable("plantBarCode") String barCode){
        LOGGER.info(barCode);
        model.addAttribute("plant", plantManager.getPlantByBarcode(barCode));
        return new ModelAndView("plants/plantdetail");
    }
}
