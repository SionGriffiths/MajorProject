package com.siongriffiths.nppcdatavisualiser.configuration;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by sig2 on 30/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ApplicationConfiguration is a configuration bean used to override and specify behaviour or
 * routes which do not require an entire controller to be implemented.
 */
@Component
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Add view controllers defines new view controllers without having to speficy them in their own
     * class using the @Controller annotation. Use for when no extra functionality aside from route
     * definition is required.
     * @param registry the Spring context ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
