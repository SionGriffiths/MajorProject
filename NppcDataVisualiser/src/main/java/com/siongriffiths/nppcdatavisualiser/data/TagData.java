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

    private long id;

    private String tagContent;


    public TagData(){
        new TagData("");
    }

    public TagData(String content){
        tagContent = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagData tagData = (TagData) o;

        return id == tagData.id && tagContent.equals(tagData.tagContent);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + tagContent.hashCode();
        return result;
    }
}
