package com.siongriffiths.nppcdatavisualiser.system.service;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface InitialisationService {

    void initExperiment(String experimentCode);
    void initData(String experimentCode);
    void resetData(String experimentCode);
    void deleteExperiementData(String experimentCode);
}
