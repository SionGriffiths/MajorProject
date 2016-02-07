package com.siongriffiths.plantatlas.helloplants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sig2 on 07/02/2016.
 */
@Controller
public class PlantsPageController {

    @RequestMapping("/hello")
    public String hello(Model model){
        return "hello";
    }

}
