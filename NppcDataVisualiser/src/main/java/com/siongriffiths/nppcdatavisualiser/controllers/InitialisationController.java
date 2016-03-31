package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.system.service.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
     * ExperimentManager provides access to experiment specific logic and persistence layer
     */
    @Autowired
    private ExperimentManager experimentManager;


    //http://stackoverflow.com/questions/12576156/reading-a-list-from-properties-file-and-load-with-spring-annotation-value
    @Value("#{'${available.experiment.codes}'.split(',')}")
    private List<String> experimentCodes;

    private List<Experiment> experimentList;

    @RequestMapping
    public String showInit(Model model){
        populateExperimentCodesIntoModel(model);
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/createPlants/{experimentCode}")
    public String createPlants(@PathVariable("experimentCode") String experimentCode, Model model) {
        logger.info("Initialise experiment with code " + experimentCode);
        populateExperimentCodesIntoModel(model);
        if(Boolean.FALSE.equals(initialisationService.getInitilisedStatus())) {
            initialisationService.initExperiment(experimentCode);
        }
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/deletePlants")
    public String deletePlants(@PathVariable("experimentCode") String experimentCode, Model model){
        populateExperimentCodesIntoModel(model);
        initialisationService.deleteExperiementData();
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/dataImport/{experimentCode}")
    public String importMetaData(@PathVariable("experimentCode") String experimentCode, Model model){
        initialisationService.initData(experimentCode);
        populateExperimentCodesIntoModel(model);
        return INIT_PAGE_PATH;
    }

    @RequestMapping("/resetData/{experimentCode}")
    public String resetData(@PathVariable("experimentCode") String experimentCode, Model model){
        populateExperimentCodesIntoModel(model);
        initialisationService.resetData();
        return INIT_PAGE_PATH;
    }

    private void populateExperimentCodesIntoModel(Model model){
        model.addAttribute("experimentCodes",experimentCodes);
    }
}
