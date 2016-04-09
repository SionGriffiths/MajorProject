package com.siongriffiths.nppcdatavisualiser.controllers;

import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created on 23/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * MVC controller class which provides the Home page view.
 * All requests to the base URL '/' will be processed by this conroller
 */

@Controller
@RequestMapping("/")
public class HomePageController extends DefaultController {


    @Autowired
    private ExperimentManager experimentManager;

    /**
     * View paths used by this controller
     */
    private static final String DEFAULT_HOME_PATH = "home/default";
    private static final String PLANT_PAGE_REDIRECT = "redirect:/plants";

    /**
     * showHome returns the default home view
     * @param session the HttpSession
     * @return the dafault view path for the home page
     */
    // adapt session handling from official spring boot samples :
    // https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-session-redis
    @RequestMapping
    public String showHome(HttpSession session, Model model) {
        model.addAttribute("experimentList", experimentManager.getInitialisedExperiments());
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return DEFAULT_HOME_PATH;
    }

    /**
     * Sets the currently selected experiment in the session
     * @param session the HttpSession
     * @param experimentCode The code identifying the experiment
     * @return
     */
    @RequestMapping("/setSessionExperiment/{experimentCode}")
    public String setExperiment(HttpSession session, @PathVariable("experimentCode") String experimentCode){
        session.setAttribute("experimentCode",experimentCode);
        return PLANT_PAGE_REDIRECT;
    }
}
