package com.siongriffiths.nppcdatavisualiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created on 23/02/2016.
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Application class contains the main method and is system entrypoint. SpringBootApplication annotation pfovides
 * some bootstrapped configurations and provides the application context for the system.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
