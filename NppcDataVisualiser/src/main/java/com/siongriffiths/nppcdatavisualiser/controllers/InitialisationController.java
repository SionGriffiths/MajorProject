package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.imageutils.ImageLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/init")
public class InitialisationController {

    public static final Logger LOGGER = Logger.getLogger(InitialisationController.class);

    @Autowired
    private ImageLoader imageLoader;

    @RequestMapping
    public ModelAndView showInit(){
        return new ModelAndView("init/default");
    }

    @RequestMapping("/createPlants")
    public ModelAndView createPlants() {
        LOGGER.info("Plants init");
        imageLoader.testing();
        return new ModelAndView("init/default");
    }

}
