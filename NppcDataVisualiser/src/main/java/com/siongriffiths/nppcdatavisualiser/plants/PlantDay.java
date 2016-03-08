package com.siongriffiths.nppcdatavisualiser.plants;

import javax.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @OneToMany(mappedBy = "plantDay", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PlantImage> plantImages;

    public PlantDay(){}



}
