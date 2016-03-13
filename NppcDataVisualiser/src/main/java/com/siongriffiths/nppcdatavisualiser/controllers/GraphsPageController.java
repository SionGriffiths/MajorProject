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

    private static final String GRAPHS_SHOW_PATH = "graphs/show";


    @RequestMapping
    public String showGraphsPage(){
        return GRAPHS_SHOW_PATH;
    }

}
