package com.siongriffiths.nppcdatavisualiser.controlobjects;

/**
 * Created on 02/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * POJO class used to represent a front end form and act as a data transfer object for form submission
 */
public class PlantDetailsForm {

    private String tagContent;
    private String plantImageID;

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public String getPlantImageID() {
        return plantImageID;
    }

    public void setPlantImageID(String plantImageID) {
        this.plantImageID = plantImageID;
    }
}
