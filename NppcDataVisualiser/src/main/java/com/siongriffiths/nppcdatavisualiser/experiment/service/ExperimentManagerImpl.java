package com.siongriffiths.nppcdatavisualiser.experiment.service;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.daos.ExperimentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 */
public class ExperimentManagerImpl implements ExperimentManager {

    @Autowired
    private ExperimentDao experimentDao;

    @Override
    public List<Experiment> getAllExperiments() {
        return experimentDao.findAll();
    }
}
