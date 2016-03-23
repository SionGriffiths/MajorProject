package com.siongriffiths.nppcdatavisualiser.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 29/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ErrorPageController is an implementation of ErrorController that provides the means
 * to override the default error page provided by Spring
 */
@Controller
public class ErrorPageController extends DefaultController implements ErrorController {

    /**
     * View paths used by this controller
     */
    private static final String ERROR_PATH = "/error";

    /**
     * Method provides a path to a view template to be used when the system throws an error
     * @return A String representing the path to the view template
     */
    @RequestMapping(value = ERROR_PATH)
    public String showError(){
        return "error/default";
    }

    /**
     * Gets the path to the error view template
     * @return the error view path as a String
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
