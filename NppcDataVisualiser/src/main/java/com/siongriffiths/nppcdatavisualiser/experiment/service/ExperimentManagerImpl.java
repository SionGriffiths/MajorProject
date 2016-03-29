package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
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
      return experimentDao.findByInitialised(true);
    }


    @Override
    public Experiment getExperimentByCode(String experimentCode) {
        return experimentDao.findByExperimentCode(experimentCode);
    }

    @Override
    public Experiment createNewExperiment(String experimentCode) {
        return new Experiment(experimentCode);
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
