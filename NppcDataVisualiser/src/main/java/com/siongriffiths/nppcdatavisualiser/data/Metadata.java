package com.siongriffiths.nppcdatavisualiser.data;



import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Entity
@Indexed
public class Metadata{


    private Integer id;

    private Map<String,String> dataAttributes;

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


    @ElementCollection
    public Map<String, String> getDataAttributes() {
        return dataAttributes;
    }

    public void setDataAttributes(Map<String, String> dataAttributes) {
        this.dataAttributes = dataAttributes;
    }

}
