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

    // TODO: 28/02/2016  - check if eager fetch causes performance issues when many images involved 
    @OneToMany(mappedBy = "plant", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PlantImage> plantImages;

    @Id
    @Column(name = "bar_code")
    private String barCode;

    //Default constructor required for mapping from DB
    public Plant(){}

    public Plant(String barCode){
        this.barCode = barCode;
        plantMetaData = new Metadata();
        plantImages = new ArrayList<>();
    }

    public Metadata getPlantMetaData() {
        return plantMetaData;
    }

    public void setPlantMetaData(Metadata plantMetaData) {
        this.plantMetaData = plantMetaData;
    }

    public List<PlantImage> getPlantImages() {
        return plantImages;
    }

    public void setPlantImages(ArrayList<PlantImage> plantImages) {
        this.plantImages = plantImages;
    }

    public void addPlantImage(PlantImage plantImage){
        plantImages.add(plantImage);
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
