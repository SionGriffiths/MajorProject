package com.siongriffiths.nppcdatavisualiser.plants.controlobjects;

/**
 * Created on 12/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Form backing object pojo class used to represent a front end form and act as a data transfer object for form submission
 */
public class PlantDayAttributeInfo {

    private String plantDayID;
    private String attribName;
    private String attribVal;

    public String getPlantDayID() {
        return plantDayID;
    }

    public void setPlantDayID(String plantDayID) {
        this.plantDayID = plantDayID;
    }

    public String getAttribName() {
        return attribName;
    }

    public void setAttribName(String attribName) {
        this.attribName = attribName;
    }

    public String getAttribVal() {
        return attribVal;
    }

    public void setAttribVal(String attribVal) {
        this.attribVal = attribVal;
    }
}
