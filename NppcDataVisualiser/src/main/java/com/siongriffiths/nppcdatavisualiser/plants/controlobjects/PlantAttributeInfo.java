package com.siongriffiths.nppcdatavisualiser.plants.controlobjects;

/**
 * Created by sig2 on 06/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Form backing object pojo class used to represent a front end form and act as a data transfer object for form submission
 */
public class PlantAttributeInfo {

    private String plantID;
    private String attribName;
    private String attribVal;

    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
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
