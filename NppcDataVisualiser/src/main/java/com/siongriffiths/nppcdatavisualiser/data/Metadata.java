package com.siongriffiths.nppcdatavisualiser.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Entity
public class Metadata implements Serializable {


    private Integer id;

    // TODO: 11/03/2016 find out a reasonable size for this column length
    @Column(length=100000)
    private HashMap<String,String> dataAttributes;

    public Metadata(){
        dataAttributes = new HashMap<>();
        dataAttributes.put("Growth Stage","0"); //// TODO: 11/03/2016 Move default values to experiment config properties?
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addDataAttribute(String key, String value){
        dataAttributes.put(key,value);
    }

    public HashMap<String, String> getDataAttributes() {
        return dataAttributes;
    }

    public void setDataAttributes(HashMap<String, String> dataAttributes) {
        this.dataAttributes = dataAttributes;
    }

    public String getGrowthStage() {
        return dataAttributes.get("Growth Stage");
    }

    public void setGrowthStage(String growthStage) {
        dataAttributes.put("Growth Stage", growthStage);
    }
}
