package com.siongriffiths.nppcdatavisualiser.experiment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
