package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.daos.ExperimentDao;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 */
@Service("experimentManager")
public class ExperimentManagerImpl implements ExperimentManager {

    @Autowired
    private ExperimentDao experimentDao;

    @Override
    public List<Experiment> getAllExperiments() {
        return experimentDao.findAll();
    }

    @Override
    public List<Experiment> getInitialisedExperiments() {
      return experimentDao.findByStatus(ExperimentStatus.INITIALISED);
    }


    @Override
    public Experiment getExperimentByCode(String experimentCode) {
        return experimentDao.findByExperimentCode(experimentCode);
    }

    @Override
    public Experiment createNewExperiment(String experimentCode) {
        Experiment experiment = new Experiment(experimentCode);
        experiment.setStatus(ExperimentStatus.NOT_INITIALISED);
        return experiment;
    }

    @Override
    public Experiment getOrCreateNewExperiment(String experimentCode) {
        Experiment experiment = getExperimentByCode(experimentCode);
        if(experiment == null){
            return createNewExperiment(experimentCode);
        } else {
            return experiment;
        }
    }

    @Override
    public ExperimentStatus getExperimentStatus(String experimentCode) {
        ExperimentStatus status = ExperimentStatus.NOT_INITIALISED;
        Experiment experiment = getExperimentByCode(experimentCode);

        if(experiment != null){
            status = experiment.getStatus();
        }

        return status;
    }

    @Override
    public void addPlantToExperiment(Experiment experiment, Plant plant) {
        experiment.addPlant(plant);
    }

    @Override
    public void saveExperiment(Experiment experiment) {
        experimentDao.save(experiment);
    }


}
