package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 * The PlantImageManager interface defines the public business processes for managing PlantImage objects
 * and their interactions within the system.
 */
public interface PlantImageManager {

    /**
     * Finds a PlantImage associated with a particular file path or creates one if it doesn't exist
     * @param path the file path
     * @return the retrieved or created PlantImage
     */
    PlantImage getOrCreatePlantImageByPath(String path);
}
