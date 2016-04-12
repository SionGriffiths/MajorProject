package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The ExperimentManager interface defines public services and business logic for
 * managing Experiments within the system
 */
public interface ExperimentManager {

    /**
     * Gets all experiments in the system
     * @return a list of all experiments
     */
    List<Experiment> getAllExperiments();

    /**
     * Gets all currently initialised experiments
     * @return a list of currently initialised experiements
     */
    List<Experiment> getInitialisedExperiments();

    /**
     * Gets the experiement corresponding to a particular code
     * @param experimentCode the experiment code
     * @return the experiment corresponding to the code
     */
    Experiment getExperimentByCode(String experimentCode);

    /**
     * Creates a new experiment with a specific code
     * @param experimentCode the code
     * @return the new experiemnt instance
     */
    Experiment createNewExperiment(String experimentCode);

    /**
     * Find an experiment with a given code or creates a new one
     * @param experimentCode the code
     * @return the found or created experiment
     */
    Experiment getOrCreateNewExperiment(String experimentCode);

    /**
     * Gets the status of a particular experiemnet with a given code
     * @param experimentCode the code
     * @return the status of the experiement
     */
    ExperimentStatus getExperimentStatus(String experimentCode);

    /**
     * Updates the status of a given experiement
     * @param experiment the experiment
     * @param status the status
     */
    void updateStatus(Experiment experiment, ExperimentStatus status);

    /**
     * Associates a Plant instance to a particular experiment
     * @param experiment the experiment
     * @param plant the plant
     */
    void addPlantToExperiment(Experiment experiment, Plant plant);

    /**
     * Persists an experiement
     * @param experiment the experiment
     */
    void saveExperiment(Experiment experiment);
}
