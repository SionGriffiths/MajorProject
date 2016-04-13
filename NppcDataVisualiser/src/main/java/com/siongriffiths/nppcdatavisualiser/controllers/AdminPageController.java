package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * InitialisationController is an MVC controller that processes all requests to the '/admin' url.
 * InitialisationController provides access to administrative tasks such as loading plants from the data store
 * and handling experiment data import
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController extends DefaultController {

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
     * ExperimentManager provides access to experiment specific logic and persistence layer
     */
    @Autowired
    private ExperimentManager experimentManager;

    /**
     *  The list of experiment codes available to the system, defined in the application.properties file.
     *  Adapted from http://stackoverflow.com/questions/12576156/reading-a-list-from-properties-file-and-load-with-spring-annotation-value
     */
    @Value("#{'${available.experiment.codes}'.split(',')}")
    private List<String> experimentCodes;


    /**
     * Shows the admin page
     * @param model the page model object
     * @return view path to the default admin view
     */
    @RequestMapping
    public String showInit(Model model){
        populateExperimentCodesIntoModel(model);
        return INIT_PAGE_PATH;
    }

    /**
     * Initialises an experiment. Creates, populates and persists model objects for the experiment
     * @param experimentCode the code for the experiment to initialise
     * @param model the page model object
     * @return view path to the default admin view
     */
    @RequestMapping("/createPlants/{experimentCode}")
    public String initialiseExperiment(@PathVariable("experimentCode") String experimentCode, Model model) {
        logger.info("Initialise experiment with code " + experimentCode);

        populateExperimentCodesIntoModel(model);
        initialisationService.initExperiment(experimentCode);

        return INIT_PAGE_PATH;
    }

    /**
     * Deletes the model objects associated with an experiment
     * @param experimentCode the code for the experiment
     * @param model the page model object
     * @return view path to the default admin view
     */
    @RequestMapping("/deletePlants/{experimentCode}")
    public String deletePlants(@PathVariable("experimentCode") String experimentCode, Model model){
        populateExperimentCodesIntoModel(model);
        initialisationService.deleteExperiement(experimentCode);
        return INIT_PAGE_PATH;
    }

    /**
     * Imports data from file to enrich experiment. Uses a convention over configuration approach to file locations
     * @param experimentCode the code for the experiment
     * @param model the page model object
     * @return view path to the default admin view
     */
    @RequestMapping("/dataImport/{experimentCode}")
    public String importMetaData(@PathVariable("experimentCode") String experimentCode, Model model){
        initialisationService.initData(experimentCode);
        populateExperimentCodesIntoModel(model);
        return INIT_PAGE_PATH;
    }

    /**
     * Deletes the enrichment data associated with an experiment
     * @param experimentCode the code for the experiment
     * @param model the page model object
     * @return view path to the default admin view
     */
    @RequestMapping("/resetData/{experimentCode}")
    public String resetData(@PathVariable("experimentCode") String experimentCode, Model model){
        populateExperimentCodesIntoModel(model);
        initialisationService.resetData(experimentCode);
        return INIT_PAGE_PATH;
    }

    /**
     * Populates the experiment codes available to the system into the page model object
     * @param model the page model object
     */
    private void populateExperimentCodesIntoModel(Model model){
        Map<String, ExperimentStatus> statusMap = new HashMap<>();
        for(String code : experimentCodes){
            statusMap.put(code,experimentManager.getExperimentStatus(code));
        }
        model.addAttribute("statusMap" , statusMap);
    }
}
