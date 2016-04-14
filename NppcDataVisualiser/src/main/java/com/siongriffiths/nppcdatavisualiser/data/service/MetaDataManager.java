package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;

import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The MetaDataManager interface defines the public business processes for managing Metadata objects
 * and their interactions within the system.
 */
public interface MetaDataManager {

    /**
     * Finds all Metadata instances in the system
     * @return a list of all Moetadata instances
     */
    List<Metadata> findAll();

    /**
     * Finds all Metadata instances for a given Experiment
     * @param experiment an Experiment instance
     * @return a list of all Metadata instances for the given Experiment
     */
    List<Metadata> findByExperiment(Experiment experiment);

    /**
     * Resets all Metadata instances for a given Experiment
     * @param experiment an Experiment instance
     */
    void resetByExperiment(Experiment experiment);

    /**
     * Persists a Metadata instance
     * @param data the Metadata instance to be persisted
     */
    void saveMetaData(Metadata data);

}
