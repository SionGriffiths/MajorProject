package com.siongriffiths.nppcdatavisualiser.experiment;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"experiment_code"}))
public class Experiment {

    private Integer id;

    private String experimentCode;

    private List<Plant> plants;

    private ExperimentStatus status;

    public Experiment(){
        plants = new ArrayList<>();
    }

    public Experiment(String experimentCode){
        this();
        this.experimentCode = experimentCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "experiment_code")
    public String getExperimentCode() {
        return experimentCode;
    }

    public void setExperimentCode(String experimentCode) {
        this.experimentCode = experimentCode;
    }

    public void addPlant(Plant plant){
        plants.add(plant);
    }

    @OneToMany(mappedBy = "experiment",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    @Enumerated(EnumType.STRING)
    public ExperimentStatus getStatus() {
        return status;
    }

    public void setStatus(ExperimentStatus status) {
        this.status = status;
    }

}
