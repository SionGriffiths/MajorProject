package com.siongriffiths.nppcdatavisualiser.configuration;

import com.siongriffiths.nppcdatavisualiser.imageutils.ImageLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 23/02/2016.
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Configuration
public class BeanConfigs {

    @Bean
    public ImageLoader imageLoader(){
        return new ImageLoader();
    }


}
