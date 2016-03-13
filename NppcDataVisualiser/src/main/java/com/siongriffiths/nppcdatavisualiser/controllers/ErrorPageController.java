package com.siongriffiths.nppcdatavisualiser.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 29/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Controller
public class ErrorPageController extends DefaultController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String showError(){
        return "error/default";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
