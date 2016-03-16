package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.imageutils.ImageLoader;
import com.siongriffiths.nppcdatavisualiser.utils.ExperimentCSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("initialisationService")
public class InitialisationServiceImpl implements InitialisationService {

    private Boolean systemInitialisedFlag = Boolean.FALSE;


    @Autowired
    private ImageLoader imageLoader;
    @Autowired
    private ExperimentCSVReader experimentCSVReader;
    @Autowired
    private MetaDataManager metaDataManager;
    @Autowired
    private TagManager tagManager;

    public void initSystem(){
        imageLoader.initPlantImages();
        setSystemInitialisedFlag(Boolean.TRUE);
    }

    public void initData(){
        experimentCSVReader.doParse();
    }

    @Override
    public void resetData() {
        metaDataManager.resetAll();
        tagManager.resetAll();
    }

    @Override
    public Boolean getInitilisedStatus() {
        return systemInitialisedFlag;
    }

    public void setSystemInitialisedFlag(Boolean systemInitialisedFlag) {
        this.systemInitialisedFlag = systemInitialisedFlag;
    }
}

