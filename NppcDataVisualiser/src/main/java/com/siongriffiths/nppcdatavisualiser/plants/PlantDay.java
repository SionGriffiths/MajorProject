package com.siongriffiths.nppcdatavisualiser.plants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Represents a day in the life of a plant. Essentailly a wrapper for the many images per day of a given plant.
 */
@Entity
public class PlantDay {


    private long id;


    private Plant plant;


    private List<PlantImage> plantImages;

    private Date date;

    public PlantDay(){
    }

    public PlantDay(Date date){
        this.date = date;
        plantImages = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @OneToMany(mappedBy = "plantDay", cascade = {CascadeType.ALL})
    public List<PlantImage> getPlantImages() {
        return plantImages;
    }

    public void addPlantImage(PlantImage plantImage){
        plantImages.add(plantImage);
    }

    public void setPlantImages(List<PlantImage> plantImages) {
        this.plantImages = plantImages;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
