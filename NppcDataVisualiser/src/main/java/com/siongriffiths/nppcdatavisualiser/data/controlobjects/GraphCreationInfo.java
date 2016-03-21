package com.siongriffiths.nppcdatavisualiser.data.controlobjects;

/**
 * Created on 21/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class GraphCreationInfo {


    private String xAxisAttribute;
    private String yAxisAttribute;

    public String getxAxisAttribute() {
        return xAxisAttribute;
    }

    public void setxAxisAttribute(String xAxisAttribute) {
        this.xAxisAttribute = xAxisAttribute;
    }

    public String getyAxisAttribute() {
        return yAxisAttribute;
    }

    public void setyAxisAttribute(String yAxisAttribute) {
        this.yAxisAttribute = yAxisAttribute;
    }
}
