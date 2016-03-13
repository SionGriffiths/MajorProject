package com.siongriffiths.nppcdatavisualiser.data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Entity
public class Metadata{


    private Integer id;
    //http://stackoverflow.com/questions/7876724/how-to-return-mapkey-value-with-hql


    // TODO: 11/03/2016 find out a reasonable size for this column length

    //Hibernate requires that persistent collection-valued fields be declared as an interface

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

    //http://stackoverflow.com/questions/3393649/storing-a-mapstring-string-using-jpa
    @ElementCollection
    public Map<String, String> getDataAttributes() {
        return dataAttributes;
    }

    public void setDataAttributes(Map<String, String> dataAttributes) {
        this.dataAttributes = dataAttributes;
    }

}
