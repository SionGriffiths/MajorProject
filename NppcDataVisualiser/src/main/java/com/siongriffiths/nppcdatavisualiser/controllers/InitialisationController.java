package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/init")
public class InitialisationController {

    private static final Logger LOGGER = Logger.getLogger(InitialisationController.class);

    @Autowired
    InitialisationService initialisationService;

    @RequestMapping
    public String showInit(){
        return "init/default";
    }

    @RequestMapping("/createPlants")
    public String createPlants() {
        LOGGER.info("Plants init");
        if(Boolean.FALSE.equals(initialisationService.getInitilisedStatus())) {
            initialisationService.initSystem();
        }
        return "init/default";
    }

}
