package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String barCode;

    //Default constructor required for mapping from orm
    public Plant(){}

    public Plant(String barCode){
        this.barCode = barCode;
        plantMetaData = new Metadata();
        plantDays = new ArrayList<>();
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

    //Accessor is annotated to avoid eager fetch on object creation
    @OneToMany(mappedBy = "plant", cascade = {CascadeType.ALL})
    public List<PlantDay> getPlantDays() {
        return plantDays;
    }


    public void setPlantDays(List<PlantDay> plantDays) {
        this.plantDays = plantDays;
    }

    @Column(name = "bar_code")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
