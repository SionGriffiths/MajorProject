package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.doas.MetaDataDao;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * MetaDataManagerImpl implements all publicly defined business processes defined in MetaDataManager
 */
@Transactional
@Service("metaDataManager")
public class MetaDataManagerImpl implements MetaDataManager {

    /**
     * The MetaDataDao repository used to provide persistence layer interactions for Metadata objects
     */
    @Autowired
    private MetaDataDao metaDataDao;

    /**
     * Finds all Metadata instances in the system
     * @return a list of all Moetadata instances
     */
    @Override
    public List<Metadata> findAll(){
        return metaDataDao.findAll();
    }

    /**
     * Finds all Metadata instances for a given Experiment
     * @param experiment an Experiment instance
     * @return a list of all Metadata instances for the given Experiment
     */
    @Override
    public List<Metadata> findByExperiment(Experiment experiment) {
        List<Metadata> dataList = metaDataDao.findByExperimentForPlant(experiment);
        dataList.addAll(metaDataDao.findByExperimentForPlantDay(experiment));
        return dataList;
    }

    /**
     * Resets all Metadata instances for a given Experiment
     * @param experiment an Experiment instance
     */
    @Override
    public void resetByExperiment(Experiment experiment) {
        for(Metadata data : findByExperiment(experiment)){
            data.setDataAttributes(new HashMap<String, String>());
            saveMetaData(data);
        }
    }



    /**
     * Persists a Metadata instance
     * @param data the Metadata instance to be persisted
     */
    @Override
    public void saveMetaData(Metadata data) {
        metaDataDao.save(data);
    }


}
