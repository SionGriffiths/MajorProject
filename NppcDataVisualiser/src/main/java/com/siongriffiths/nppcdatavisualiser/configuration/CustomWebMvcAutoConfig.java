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
 *
 * http://stackoverflow.com/questions/21123437/how-do-i-use-spring-boot-to-serve-static-content-located-in-dropbox-folder/26939359#26939359
 */
@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Value("${image-repository.root.static-link}")
    private String imageRepoRoot;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String myExternalFilePath = imageRepoRoot;

        registry.addResourceHandler("/images/**").addResourceLocations(myExternalFilePath);

        super.addResourceHandlers(registry);
    }

}