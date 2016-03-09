package com.siongriffiths.nppcdatavisualiser.data;

import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;

import javax.persistence.*;
import java.util.Set;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
public class TagData {

    private long id;

    private String tagContent;


    private Set<PlantDay> taggedImages;


    public TagData(){}

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

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="tags")
    public Set<PlantDay> getTaggedImages() {
        return taggedImages;
    }

    public void setTaggedImages(Set<PlantDay> taggedImages) {
        this.taggedImages = taggedImages;
    }
}
