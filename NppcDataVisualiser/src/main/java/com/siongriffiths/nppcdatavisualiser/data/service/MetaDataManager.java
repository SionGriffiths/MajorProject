package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;

import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface MetaDataManager {

    List<Metadata> findAll();
    void resetAll();

    void saveMetaData(Metadata data);

}
