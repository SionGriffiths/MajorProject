package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface PlantImageManager {

    void savePlantImage(PlantImage plantImage);
    //    void addTagData(TagData tagData)
    PlantImage getPlantImageByID(long id);
    void tagPlantImage(TagData tag, PlantImage image);
}
