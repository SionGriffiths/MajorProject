package com.siongriffiths.nppcdatavisualiser.experiment.daos;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sig2 on 28/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ExperimentDao is a spring data repository class providing persistence layer access to the system for
 * Experiment objects.
 */
public interface ExperimentDao extends JpaRepository<Experiment,Integer> {

    /**
     * Finds an experiment instance associated with a given experiment code
     * @param experimentCode the experiment code
     * @return an experiment instance corresponding to the parameter code
     */
    Experiment findByExperimentCode(String experimentCode);

    /**
     * Returns all experiemnts which have a given status
     * @param status the Experiment status
     * @return all experiemnt which have the target status
     */
    List<Experiment> findByStatus(ExperimentStatus status);
}
