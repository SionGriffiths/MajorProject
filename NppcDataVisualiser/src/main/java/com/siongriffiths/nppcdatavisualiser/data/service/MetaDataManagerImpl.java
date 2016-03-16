package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.doas.MetaDataDao;
import org.hibernate.metamodel.domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("metaDataManager")
public class MetaDataManagerImpl implements MetaDataManager {

    @Autowired
    MetaDataDao metaDataDao;

    @Override
    public List<Metadata> findAll(){
        return metaDataDao.findAll();
    }

    @Override
    public void resetAll() {

        for(Metadata data : findAll()){
            data.setDataAttributes(new HashMap<String, String>());
            metaDataDao.save(data);
        }

    }

    @Override
    public void saveMetaData(Metadata data) {
        metaDataDao.save(data);
    }


}
