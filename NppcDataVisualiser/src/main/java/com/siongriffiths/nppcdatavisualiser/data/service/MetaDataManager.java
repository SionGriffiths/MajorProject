package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;

import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface MetaDataManager {

    List<Metadata> findAll();
    List<Metadata> findByExperiment(Experiment experimentCode);
    void resetAll();
    void resetByExperiment(Experiment experiment);
    void saveMetaData(Metadata data);

}
