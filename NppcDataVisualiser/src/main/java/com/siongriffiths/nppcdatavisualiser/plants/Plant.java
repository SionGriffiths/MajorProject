package com.siongriffiths.nppcdatavisualiser.plants;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created on 26/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Entity
@Table(name="plants", uniqueConstraints = @UniqueConstraint(columnNames = {"bar_code"}))
public class Plant {


    @OneToOne
    @JoinColumn(name="plant_meta_data_id")
    private Metadata plantMetaData;

    @Column(name = "plant_images")
    private ArrayList<PlantImage> plantImages;

    @Id
    @Column(name = "bar_code")
    private String barCode;


    public Plant(String barCode){
        this.barCode = barCode;
    }
//
//    public Metadata getPlantMetaData() {
//        return plantMetaData;
//    }
//
//    public void setPlantMetaData(Metadata plantMetaData) {
//        this.plantMetaData = plantMetaData;
//    }

    public ArrayList<PlantImage> getPlantImages() {
        return plantImages;
    }

    public void setPlantImages(ArrayList<PlantImage> plantImages) {
        this.plantImages = plantImages;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
