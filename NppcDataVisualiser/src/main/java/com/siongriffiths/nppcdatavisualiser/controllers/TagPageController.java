package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
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

import java.util.List;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/tags")
public class TagPageController extends DefaultController{

    private static final String TAGS_SHOW_PATH = "tags/show";
    private static final String TAGS_RESULT_PATH = "tags/result";
    private static final String TAG_NOT_FOUND_PATH = "tags/notfound";


    @Autowired
    TagManager tagManager;
    @Autowired
    PlantDayManager plantDayManager;
    @Autowired
    PlantManager plantManager;

    @RequestMapping
    public String showTags(Model model){
        model.addAttribute("tags", tagManager.getAllTags());

        return TAGS_SHOW_PATH;
    }

    @RequestMapping(value = "{tagContent}",method = RequestMethod.GET)
    public String displayTagResult(Model model, @PathVariable("tagContent") String tagContent){
        logger.info(tagContent);

        String viewPath;

        TagData tag = tagManager.getTagByContent(tagContent);

        if(tag == null){
            model.addAttribute("content", tagContent);
            viewPath = TAG_NOT_FOUND_PATH;
        }else {
            List<PlantDay> taggedDays = plantDayManager.findPlantDaysByTag(tag);
            List<Plant> taggedPlants = plantManager.findPlantsByTag(tag);
            model.addAttribute("tag",tag);
            model.addAttribute("taggedDays",taggedDays);
            model.addAttribute("taggedPlants",taggedPlants);
            viewPath = TAGS_RESULT_PATH;
        }

        return viewPath;
    }



}
