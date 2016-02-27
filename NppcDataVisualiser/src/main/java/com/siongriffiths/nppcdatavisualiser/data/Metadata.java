package com.siongriffiths.nppcdatavisualiser.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Entity
public class Metadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private HashMap<String,String> dataAttributes;

    public Metadata(){
        dataAttributes = new HashMap<>();
    }

    public void addDataAttribute(String key, String value){
        dataAttributes.put(key,value);
    }

    public HashMap<String, String> getDataAttributes() {
        return dataAttributes;
    }

}
