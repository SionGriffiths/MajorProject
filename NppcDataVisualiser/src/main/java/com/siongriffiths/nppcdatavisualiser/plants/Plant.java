package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bar_code"}))
public class Plant {

    private long id;
    private Metadata plantMetaData;
    private List<PlantDay> plantDays;
    private Set<TagData> tags;
    private String barCode;
    private Experiment experiment;

    //Default constructor required for mapping from orm
    public Plant(){}

    public Plant(String barCode){
        this.barCode = barCode;
        plantMetaData = new Metadata();
        plantDays = new ArrayList<>();
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

    public void addPlantDay(PlantDay plantDay){
        plantDays.add(plantDay);
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_meta_data_id")
    public Metadata getPlantMetaData() {
        return plantMetaData;
    }

    public void setPlantMetaData(Metadata plantMetaData) {
        this.plantMetaData = plantMetaData;
    }

    @OneToMany(mappedBy = "plant",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<PlantDay> getPlantDays() {
        return plantDays;
    }

    public void setPlantDays(List<PlantDay> plantDays) {
        this.plantDays = plantDays;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="plant_tag",
            joinColumns=@JoinColumn(name="plant_id"),
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

    @Column(name = "bar_code")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @ManyToOne
    @JoinColumn(name = "experiment_id")
    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }
}
