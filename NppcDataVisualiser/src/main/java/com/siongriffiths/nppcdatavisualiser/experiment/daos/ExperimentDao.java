package com.siongriffiths.nppcdatavisualiser.experiment.daos;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 */
public interface ExperimentDao extends JpaRepository<Experiment,Integer> {

    Experiment findByExperimentCode(String experimentCode);

    List<Experiment> findByStatus(ExperimentStatus status);
}
