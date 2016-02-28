package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;

import javax.persistence.*;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"filePath"}))
public class PlantImage {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="plant_image_meta_data_id")
    private Metadata plantImageMetaData;

    @Id
    @Column
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    public PlantImage(){}

    public PlantImage(String filepath){
        this.filePath = filepath;
        plantImageMetaData = new Metadata();
    }



    public Metadata getPlantImageMetaData() {
        return plantImageMetaData;
    }

    public void setPlantImageMetaData(Metadata plantImageMetaData) {
        this.plantImageMetaData = plantImageMetaData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
