package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;

import javax.persistence.*;
import java.util.*;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Represents a day in the life of a plant. Essentailly a wrapper for the many images per day of a given plant.
 */
@Entity
public class PlantDay implements Comparable<PlantDay>{


    private long id;

    private List<PlantImage> plantImages;

    private Date date;

    private Set<TagData> tags;

    private Metadata plantDayMetaData;

    public PlantDay(){
        new PlantDay(new Date());
    }

    public PlantDay(Date date){
        this.date = date;
        plantImages = new ArrayList<>();
        tags = new HashSet<>();
        plantDayMetaData = new Metadata();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "plantDay", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    public List<PlantImage> getPlantImages() {
        return plantImages;
    }

    public void addPlantImage(PlantImage plantImage){
        plantImages.add(plantImage);
    }

    public void setPlantImages(List<PlantImage> plantImages) {
        this.plantImages = plantImages;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_day_meta_data_id")
    public Metadata getPlantDayMetaData() {
        return plantDayMetaData;
    }

    public void setPlantDayMetaData(Metadata plantDayMetaData) {
        this.plantDayMetaData = plantDayMetaData;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="plantday_tag", joinColumns=@JoinColumn(name="plantday_id"),
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

    @Override
    public int compareTo(PlantDay o) {
        return this.getDate().compareTo(o.getDate());
    }


}
