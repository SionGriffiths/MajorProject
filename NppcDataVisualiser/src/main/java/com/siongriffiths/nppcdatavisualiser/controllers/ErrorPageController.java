package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.constants.NppcVisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created on 29/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ErrorPageController is an implementation of ErrorController that provides the means
 * to override the default error page provided by Spring
 *
 * Adapted from solutions posted to the following links :
 * http://stackoverflow.com/questions/25356781/spring-boot-remove-whitelabel-error-page
 * http://stackoverflow.com/a/35565623/6044187
 */
@Controller
public class ErrorPageController extends DefaultController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    /**
     * View paths used by this controller
     */
    private static final String ERROR_PATH = "/error";
    private static final String ERROR_PATH_DEFAULT = "/error/default";

    @Autowired
    public ErrorPageController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    /**
     * Method provides a path to a view template to be used when the system throws an error
     * @return A String representing the path to the view template
     */
    @RequestMapping(value = ERROR_PATH)
    public String showError(HttpServletRequest aRequest){
        Map<String, Object> body = getErrorAttributes(aRequest,
                getTraceParameter(aRequest));


        if(body.containsKey("status")){
            int status = (Integer)body.get("status");

            if(status == 404){
                return NppcVisConstants.ERROR_PATH_404;
            }
        }


        return ERROR_PATH_DEFAULT;
    }

    /**
     * Gets the path to the error view template
     * @return the error view path as a String
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }




    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}
