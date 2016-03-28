package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * InitialisationController is an MVC controller that processes all requests to the '/admin' url.
 * InitialisationController provides access to administrative tasks such as loading plants from the data store
 * and handling experiemnt data import
 */
@Controller
@RequestMapping("/admin")
public class InitialisationController extends DefaultController {

    /**
     * View paths used by this controller
     */
    private static final String INIT_PAGE_PATH = "admin/default";


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


    //http://stackoverflow.com/questions/12576156/reading-a-list-from-properties-file-and-load-with-spring-annotation-value
    @Value("#{'${available.experiment.codes}'.split(',')}")
    private List<String> experiemntList;

    @RequestMapping
    public String showInit(Model model){
        model.addAttribute("experiemntList",experiemntList);

        return INIT_PAGE_PATH;
    }

    @RequestMapping("/createPlants")
    public String createPlants() {
        logger.info("Plants admin");
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
