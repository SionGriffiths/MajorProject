package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"filePath"}))
public class PlantImage {


    private Metadata plantImageMetaData;


    private long id;


    private String filePath;


    private PlantDay plantDay;


    private Set<TagData> tags;

    public PlantImage(){}

    public PlantImage(String filepath){
        this.filePath = filepath;
        plantImageMetaData = new Metadata();
        tags = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_image_meta_data_id")
    public Metadata getPlantImageMetaData() {
        return plantImageMetaData;
    }

    public void setPlantImageMetaData(Metadata plantImageMetaData) {
        this.plantImageMetaData = plantImageMetaData;
    }

    @Column
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @ManyToOne
    @JoinColumn(name = "plant_day_id", nullable = false)
    public PlantDay getPlantDay() {
        return plantDay;
    }

    public void setPlantDay(PlantDay plantDay) {
        this.plantDay = plantDay;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="plantimage_tag", joinColumns=@JoinColumn(name="plantimage_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    public Set<TagData> getTags() {
        return tags;
    }

    public void setTags(Set<TagData> tags) {
        this.tags = tags;
    }

    public void addTag(TagData tag){
        tags.add(tag);
    }
}
