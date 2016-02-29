package com.siongriffiths.nppcdatavisualiser.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 23/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Controller
@RequestMapping("/")
public class HomePageController {

    public static final Logger LOGGER = Logger.getLogger(HomePageController.class);

    /**
     * showHome returns the default home view
     * @return the default home view
     */
    @RequestMapping
    public String showHome() {
        return "home/default";
    }
}
