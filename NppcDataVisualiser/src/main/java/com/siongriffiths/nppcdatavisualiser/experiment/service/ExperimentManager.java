package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 */
public interface ExperimentManager {

    List<Experiment> getAllExperiments();
    List<Experiment> getInitialisedExperiments();
    Experiment getExperimentByCode(String experimentCode);
    Experiment createNewExperiment(String experimentCode);
    Experiment getOrCreateNewExperiment(String experimentCode);
    ExperimentStatus getExperimentStatus(String experimentCode);
    void addPlantToExperiment(Experiment experiment, Plant plant);
    void saveExperiment(Experiment experiment);
}
