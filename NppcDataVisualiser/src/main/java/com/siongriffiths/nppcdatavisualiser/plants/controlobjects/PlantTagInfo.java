package com.siongriffiths.nppcdatavisualiser.plants.controlobjects;

/**
 * Created on 18/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Form backing object pojo class used to represent a front end form and act as a data transfer object for form submission
 */
public class PlantTagInfo {

    private String tagContent;
    private String plantID;

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }
}
