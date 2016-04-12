package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;
import java.util.Map;

/**
 * Created on 14/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The GraphingManager defines public services and business logic for
 * handling graph data creation and management and facilitating its structure
 * to facilitate handling by view components
 */
public interface GraphingManager {

    /**
     * Build a JSON like map structure from attributes available on a given plant to facilitate
     * front end view components to generate graphical visualisations
     * @param plant the Plant instance
     * @param attrib1 the first attribute
     * @param attrib2 the second attribute
     * @return a JSON like map structure containing data for graph creation
     */
    Map<String,List<String>> getGraphDataForPlant(Plant plant, String attrib1, String attrib2);

    /**
     *  * Build a JSON like map structure from attributes available on a given experiment to facilitate
     * front end view components to generate graphical visualisations
     * @param attribX the first or x axis attribute
     * @param attribY the second or y axis attribute
     * @param experimentCode code to identify current experiemnt
     * @return a JSON like map structure containing data for graph creation
     */
    Map<String,List<String>> getGraphData(String attribX, String attribY, String experimentCode);
}
