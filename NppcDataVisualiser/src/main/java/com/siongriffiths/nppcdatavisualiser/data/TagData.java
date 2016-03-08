package com.siongriffiths.nppcdatavisualiser.data;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tagContent;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="tags")
    private Set<PlantImage> taggedImages;


    public TagData(){}

    public TagData(String content){
        tagContent = content;
    }

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


    public Set<PlantImage> getTaggedImages() {
        return taggedImages;
    }

    public void setTaggedImages(Set<PlantImage> taggedImages) {
        this.taggedImages = taggedImages;
    }
}
