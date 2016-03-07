package com.siongriffiths.nppcdatavisualiser.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
public class TagData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tagContent;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }


}
