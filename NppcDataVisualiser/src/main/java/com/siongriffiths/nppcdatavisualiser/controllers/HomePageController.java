package com.siongriffiths.nppcdatavisualiser.controllers;

import org.springframework.stereotype.Controller;
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


    /**
     * View paths used by this controller
     */
    private static final String DEFAULT_HOME_PATH = "home/default";

    /**
     * showHome returns the default home view
     * @return the default home view
     */
    // adapt session handling from official spring boot samples :
    // https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-session-redis
    @RequestMapping
    public String showHome(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return DEFAULT_HOME_PATH;
    }

}
