package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
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
public class InitialisationController extends DefaultController {


    //// TODO: 16/03/2016 INIT should deal with removing previously persisted data

    @Autowired
    InitialisationService initialisationService;

    @RequestMapping
    public String showInit(){
        return "init/default";
    }

    @RequestMapping("/createPlants")
    public String createPlants() {
        logger.info("Plants init");
        if(Boolean.FALSE.equals(initialisationService.getInitilisedStatus())) {
            initialisationService.initSystem();
        }
        return "init/default";
    }

    @RequestMapping("/dataImport")
    public String importMetaData(){
        initialisationService.initData();
        return "init/default";
    }


    // // TODO: 16/03/2016 this is sooooo slow
    @RequestMapping("/resetData")
    public String resetData(){
        initialisationService.resetData();
        return "init/default";
    }
}
