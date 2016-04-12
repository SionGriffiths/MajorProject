package com.siongriffiths.nppcdatavisualiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created on 23/02/2016.
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

//        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        SpringApplication.run(Application.class, args);
    }
}
