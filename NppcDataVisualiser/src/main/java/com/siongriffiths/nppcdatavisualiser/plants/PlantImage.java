package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class PlantImage {

    private Metadata plantImageMetaData;
    private String filePath;

    public PlantImage(){}

    public PlantImage(String filepath){
        this.filePath = filepath;
    }



    public Metadata getPlantImageMetaData() {
        return plantImageMetaData;
    }

    public void setPlantImageMetaData(Metadata plantImageMetaData) {
        this.plantImageMetaData = plantImageMetaData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
