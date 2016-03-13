package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.imageutils.ImageLoader;
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


    public void initSystem(){
        imageLoader.initPlantImages();
        systemInitialisedFlag = Boolean.TRUE;
    }

    @Override
    public Boolean getInitilisedStatus() {
        return systemInitialisedFlag;
    }

    public Boolean getSystemInitialisedFlag() {
        return systemInitialisedFlag;
    }

    public void setSystemInitialisedFlag(Boolean systemInitialisedFlag) {
        this.systemInitialisedFlag = systemInitialisedFlag;
    }
}

