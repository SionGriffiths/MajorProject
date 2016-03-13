package com.siongriffiths.nppcdatavisualiser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
@RequestMapping("/graphs")
public class GraphsPageController extends DefaultController{

    @RequestMapping
    public String showGraphsPage(){
        return "graphs/show";
    }

}
