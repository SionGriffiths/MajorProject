package com.siongriffiths.nppcdatavisualiser.experiment.daos;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sig2 on 28/03/2016.
 */
public interface ExperimentDao extends JpaRepository<Experiment,Integer> {

    Experiment findByExperimentCode(String experimentCode);

}
