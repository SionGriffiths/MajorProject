package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * TagPageController is an MVC controller class that manages the tag page and associated view.
 * It provides access to services and business processes related to tags within the system
 */
@Controller
@RequestMapping("/tags")
public class TagPageController extends DefaultController{

    /**
     * View paths used by this controller
     */
    private static final String TAGS_SHOW_PATH = "tags/show";
    private static final String TAGS_RESULT_PATH = "tags/result";
    private static final String TAG_NOT_FOUND_PATH = "tags/notfound";


    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;

    /**
     * PlantDayManager, a service class providing access to business logic and persistence for PlantDay objects
     */
    @Autowired
    private PlantDayManager plantDayManager;

    /**
     * TagManager, a service class providing access to business logic and persistence for TagData objects
     */
    @Autowired
    private TagManager tagManager;

    /**
     * ExperimentManager, a service class providing access to business logic and persistence for Experiment objects
     */
    @Autowired
    private ExperimentManager experimentManager;


    /**
     * SHows the tags associated with the Experiment currently stored in teh HTTP session
     * @param model the page Model object
     * @param session the HttpSession object
     * @return view path for the tags list page
     */
    @RequestMapping
    public String showTags(Model model, HttpSession session){
        String experimentCode = (String)session.getAttribute("experimentCode");
        Experiment experiment = experimentManager.getExperimentByCode(experimentCode);
        Set<TagData> tags = tagManager.getByExperimentForPlants(experiment);
        tags.addAll(tagManager.getByExperimentForPlantDays(experiment));
        model.addAttribute("tags", tags);
        return TAGS_SHOW_PATH;
    }

    /**
     * Displays a list of plants and plantDays objects associated with a particular tag within
     * the currently selected experiment
     * @param model the page Model object
     * @param session the HttpSession object
     * @param tagContent the tag content
     * @return either a view path to tag results or not found if tag not associated with any plants or days
     */
    @RequestMapping(value = "{tagContent}",method = RequestMethod.GET)
    public String displayTagResult(Model model, @PathVariable("tagContent") String tagContent, HttpSession session){

        String viewPath;
        TagData tag = tagManager.getTagByContent(tagContent);
        String experimentCode = (String)session.getAttribute("experimentCode");
        Experiment experiment = experimentManager.getExperimentByCode(experimentCode);

        if(tag == null){
            model.addAttribute("content", tagContent);
            viewPath = TAG_NOT_FOUND_PATH;
        }else {
            List<PlantDay> taggedDays = plantDayManager.findByTagDataForExperiment(tag,experiment);
            List<Plant> taggedPlants = plantManager.findPlantsByTagForExperiment(tag,experiment);
            model.addAttribute("tag",tag);
            model.addAttribute("taggedDays",taggedDays);
            model.addAttribute("taggedPlants",taggedPlants);
            viewPath = TAGS_RESULT_PATH;
        }

        return viewPath;
    }



}
