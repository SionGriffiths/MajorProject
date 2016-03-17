package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

private static final String INIT_PAGE_PATH = "init/default";

    //// TODO: 16/03/2016 INIT should deal with removing previously persisted data

    @Autowired
  private InitialisationService initialisationService;

    @Value("${experiment.data.root.dir}")
    private String dataRoot;


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
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/deletePlants")
    public String deletePlants(){
        initialisationService.deleteExperiementData();
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/dataImport")
    public String importMetaData(){
        initialisationService.initData(dataRoot+"O7");
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/resetData")
    public String resetData(){
        initialisationService.resetData();
        return INIT_PAGE_PATH;
    }
}
