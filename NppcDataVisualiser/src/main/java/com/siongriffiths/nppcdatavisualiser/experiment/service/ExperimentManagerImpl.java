package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.daos.ExperimentDao;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ExperimentManagerImpl implements all publicly defined business processes defined in ExperimentManager
 */
@Service("experimentManager")
public class ExperimentManagerImpl implements ExperimentManager {

    /**
     * The ExperimentDao repository used to provide persistence layer interactions for Experiment objects
     */
    @Autowired
    private ExperimentDao experimentDao;

    /**
     * Gets all experiments in the system
     * @return a list of all experiments
     */
    @Override
    public List<Experiment> getAllExperiments() {
        return experimentDao.findAll();
    }

    /**
     * Gets all currently initialised experiments
     * @return a list of currently initialised experiements
     */
    @Override
    public List<Experiment> getInitialisedExperiments() {
      return experimentDao.findByStatus(ExperimentStatus.INITIALISED);
    }

    /**
     * Gets the experiement corresponding to a particular code
     * @param experimentCode the experiment code
     * @return the experiment corresponding to the code
     */
    @Override
    public Experiment getExperimentByCode(String experimentCode) {
        return experimentDao.findByExperimentCode(experimentCode);
    }

    /**
     * Creates a new experiment with a specific code
     * @param experimentCode the code
     * @return the new experiemnt instance
     */
    @Override
    public Experiment createNewExperiment(String experimentCode) {
        Experiment experiment = new Experiment(experimentCode);
        experiment.setStatus(ExperimentStatus.NOT_INITIALISED);
        return experiment;
    }

    /**
     * Find an experiment with a given code or creates a new one
     * @param experimentCode the code
     * @return the found or created experiment
     */
    @Override
    public Experiment getOrCreateNewExperiment(String experimentCode) {
        Experiment experiment = getExperimentByCode(experimentCode);
        if(experiment == null){
            return createNewExperiment(experimentCode);
        } else {
            return experiment;
        }
    }

    /**
     * Gets the status of a particular experiemnet with a given code
     * @param experimentCode the code
     * @return the status of the experiement
     */
    @Override
    public ExperimentStatus getExperimentStatus(String experimentCode) {
        ExperimentStatus status = ExperimentStatus.NOT_INITIALISED;
        Experiment experiment = getExperimentByCode(experimentCode);

        if(experiment != null){
            status = experiment.getStatus();
        }

        return status;
    }

    /**
     * Updates the status of a given experiement
     * @param experiment the experiment
     * @param status the status
     */
    @Override
    public void updateStatus(Experiment experiment, ExperimentStatus status) {
        experiment.setStatus(status);
        experimentDao.save(experiment);
    }

    /**
     * Associates a Plant instance to a particular experiment
     * @param experiment the experiment
     * @param plant the plant
     */
    @Override
    public void addPlantToExperiment(Experiment experiment, Plant plant) {
        experiment.addPlant(plant);
    }

    /**
     * Persists an experiement
     * @param experiment the experiment
     */
    @Override
    public void saveExperiment(Experiment experiment) {
        experimentDao.save(experiment);
    }


}
