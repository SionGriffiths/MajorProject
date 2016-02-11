package com.siongriffiths.plantatlas.helloplants;

import com.siongriffiths.plantatlas.imageutils.ImageLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 07/02/2016.
 * @author Sion Griffiths / sig2@aber.ac.uk
 */
@Controller
public class PlantsPageController {

    public static final Logger LOGGER = Logger.getLogger(PlantsPageController.class);

    @Autowired ImageLoader loader;

    @RequestMapping("/carousel")
    public String carousel(Model model){
        model.addAttribute("list",loader.getFiles());
        return "carousel";
    }

    @RequestMapping("/grid")
    public String grid(Model model){
        model.addAttribute("list",loader.getFiles());
        return "grid";
    }





}
