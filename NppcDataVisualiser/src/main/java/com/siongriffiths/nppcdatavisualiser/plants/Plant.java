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

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_meta_data_id")
    private Metadata plantMetaData;

    @OneToMany(mappedBy = "plant", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PlantDay> plantDays;

    @Id
    @Column(name = "bar_code")
    private String barCode;

    //Default constructor required for mapping from orm
    public Plant(){}

    public Plant(String barCode){
        this.barCode = barCode;
        plantMetaData = new Metadata();
        plantDays = new ArrayList<>();
    }

    public void addPlantDay(PlantDay plantDay){
        plantDays.add(plantDay);
    }

    public Metadata getPlantMetaData() {
        return plantMetaData;
    }

    public void setPlantMetaData(Metadata plantMetaData) {
        this.plantMetaData = plantMetaData;
    }

    public List<PlantDay> getPlantDays() {
        return plantDays;
    }


    public void setPlantDays(List<PlantDay> plantDays) {
        this.plantDays = plantDays;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
