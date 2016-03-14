package com.siongriffiths.nppcdatavisualiser.plants.controlobjects;

/**
 * Created on 12/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class PlantDayAttributeInfo {

    private String plantDayID;
    private String attribName;
    private Integer attribVal;

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

    public Integer getAttribVal() {
        return attribVal;
    }

    public void setAttribVal(Integer attribVal) {
        this.attribVal = attribVal;
    }
}
