package com.siongriffiths.nppcdatavisualiser.system.service;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The InitialisationService interface defines the public business processes for managing the intitialising of and
 * management of system experiement data
 */
public interface InitialisationService {

    /**
     * Initialises an Experiment, invokes the creation of domain model objects
     * @param experimentCode the code for the experiment
     */
    void initExperiment(String experimentCode);

    /**
     * Loads data for an experiment
     * @param experimentCode the code for the experiment
     */
    void initData(String experimentCode);

    /**
     * Resets experiment data
     * @param experimentCode the code for the experiment
     */
    void resetData(String experimentCode);

    /**
     * Deletes experiment
     * @param experimentCode the code for the experiment
     */
    void deleteExperiement(String experimentCode);
}
