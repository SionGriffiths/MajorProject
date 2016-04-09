package com.siongriffiths.nppcdatavisualiser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * CustomWebMvcAutoConfig is a configuration class which extends the default spring WebMvcAutoConfiguration
 * with the goal of defining Spring resource handlers to provide extra locations from which to serve static content
 * such as images.
 *
 * Based on the following stackOverflow post:
 * http://stackoverflow.com/questions/21123437/how-do-i-use-spring-boot-to-serve-static-content-located-in-dropbox-folder/26939359#26939359
 */
@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    /**
     * Value defined in application.properties file for definition of the imageRepository location
     */
    @Value("${image-repository.root.static-link}")
    private String imageRepoRoot;

    /**
     * Adds a resource handler to allow definition of resource locations outside of the spring default
     * for serving static content
     * @param registry The ResourceHandlerRegistry storing resource handler information
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String myExternalFilePath = imageRepoRoot;

        registry.addResourceHandler("/images/**").addResourceLocations(myExternalFilePath);

        super.addResourceHandlers(registry);
    }

}