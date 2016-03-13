package com.siongriffiths.nppcdatavisualiser.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class DefaultController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());



    @ExceptionHandler(Exception.class)
    public void handleException(
            Exception exception, HttpServletRequest request) {
        logger.error("- Exception: ", exception);
    }

}
