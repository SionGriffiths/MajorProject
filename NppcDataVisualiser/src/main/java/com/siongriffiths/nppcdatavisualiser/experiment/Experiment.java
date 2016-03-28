package com.siongriffiths.nppcdatavisualiser.experiment;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
public class Experiment {

    private Integer id;

    private String experimentCode;

    private Boolean initialised;

    private List<Plant> plants;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public Boolean getInitialised() {
        return initialised;
    }

    public void setInitialised(Boolean initialised) {
        this.initialised = initialised;
    }

    @OneToMany(mappedBy = "experiment",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
