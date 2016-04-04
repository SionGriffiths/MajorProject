package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.doas.MetaDataDao;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.hibernate.metamodel.domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
@Service("metaDataManager")
public class MetaDataManagerImpl implements MetaDataManager {

    @Autowired
    MetaDataDao metaDataDao;

    @Override
    public List<Metadata> findAll(){
        return metaDataDao.findAll();
    }

    @Override
    public List<Metadata> findByExperiment(Experiment experiment) {
        List<Metadata> dataList = metaDataDao.findByExperimentForPlant(experiment);
        dataList.addAll(metaDataDao.findByExperimentForPlantDay(experiment));
        return dataList;
    }

    @Override
    public void resetAll() {

        for(Metadata data : findAll()){
            data.setDataAttributes(new HashMap<String, String>());
            metaDataDao.save(data);
        }

    }

    @Override
    public void resetByExperiment(Experiment experiment) {
        for(Metadata data : findByExperiment(experiment)){
            data.setDataAttributes(new HashMap<String, String>());
            metaDataDao.save(data);
        }
    }


    @Override
    public void saveMetaData(Metadata data) {
        metaDataDao.save(data);
    }


}
