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
 *
 * InitialisationController is an MVC controller that processes all requests to the '/init' url.
 * InitialisationController provides access to administrative tasks such as loading plant from the data store
 * and handling experiemnt data import
 */
@Controller
@RequestMapping("/init")
public class InitialisationController extends DefaultController {

    /**
     * View paths used by this controller
     */
    private static final String INIT_PAGE_PATH = "init/default";


    /**
     * InitialisationService, provides access to system initialising functionality
     */
    @Autowired
    private InitialisationService initialisationService;

    /**
     * Property value found in default property file.
     * Contains the root directory for experiemnt data files
     */
    @Value("${experiment.data.root.dir}")
    private String dataRoot;


    @RequestMapping
    public String showInit(){
        return INIT_PAGE_PATH;
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
