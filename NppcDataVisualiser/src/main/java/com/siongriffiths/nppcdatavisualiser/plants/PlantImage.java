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

    private long id;

    private String filePath;

    private PlantDay plantDay;

    public PlantImage(){}

    public PlantImage(String filepath){
        this.filePath = filepath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
