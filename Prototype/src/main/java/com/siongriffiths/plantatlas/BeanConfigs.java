package com.siongriffiths.plantatlas;

import com.siongriffiths.plantatlas.imageutils.ImageLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sig2@aber.ac.uk on 10/02/2016.
 */
@Configuration
public class BeanConfigs {

    @Bean
    public ImageLoader imageLoader(){
        return new ImageLoader();
    }

}
