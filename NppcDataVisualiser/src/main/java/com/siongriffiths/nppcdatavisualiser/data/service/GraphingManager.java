package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;
import java.util.Map;

/**
 * Created on 14/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface GraphingManager {

    Map<String,List<String>> getGraphDataForPlant(Plant plant, String attrib1, String attrib2);

    Map<String,List<String>> getGraphData(String attribX, String attribY);
}
