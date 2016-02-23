package com.siongriffiths.nppcdatavisualiser.controllers;

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

    @RequestMapping
    public ModelAndView showHome() {
        return new ModelAndView("home/default");
    }

}
