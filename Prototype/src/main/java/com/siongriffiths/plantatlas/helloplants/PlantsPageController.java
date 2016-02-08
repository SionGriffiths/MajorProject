package com.siongriffiths.plantatlas.helloplants;

import com.siongriffiths.plantatlas.image.ImageLoader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.Properties;

/**
 * Created on 07/02/2016.
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
@Controller
public class PlantsPageController {

    public static final Logger LOGGER = Logger.getLogger(PlantsPageController.class);

    @RequestMapping("/hello")
    public String hello(Model model){

        ImageLoader loader = new ImageLoader();
        int numFile = loader.countFiles();
        LOGGER.info("THERE ARE " + numFile + " files in the target dir.");
        model.addAttribute("numFiles",numFile);
        return "hello";
    }

}
