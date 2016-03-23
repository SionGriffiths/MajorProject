package com.siongriffiths.nppcdatavisualiser.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 *
 * DefaultController provides common functionality that can be inherited by all
 * controller classes.
 */
public class DefaultController {

    /**
     * Logger instance that is shared with all subclasses to provide logging functionality
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A general exception handling method, logs the exception that may otherwise be quietly handled
     * @param exception The raised exception
     * @param request The HttpServletRequest that caused the exception
     */
    @ExceptionHandler(Exception.class)
    public void handleException(
            Exception exception, HttpServletRequest request) {
        logger.error("- Exception: ", exception);
    }

}
