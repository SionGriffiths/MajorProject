package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.data.service.ExperiemntDataImportService;
import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.imageutils.ImageLoader;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
//@Transactional
@Service("initialisationService")
public class InitialisationServiceImpl implements InitialisationService {

    private Boolean systemInitialisedFlag = Boolean.FALSE;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ImageLoader imageLoader;
    @Autowired
    private ExperiemntDataImportService experiemntDataImportService;
    @Autowired
    private MetaDataManager metaDataManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private PlantManager plantManager;

    public void initSystem(){
        imageLoader.initPlantImages();
//        setSystemInitialisedFlag(Boolean.TRUE); //// TODO: 17/03/2016 persist experiemnts so can have admin flags on plants and data, this will always be false at first run otherwise
    }

    public void initData(String experimentDir){
        experiemntDataImportService.parseAnnotatedExperiemntDataCSVFile(experimentDir+"/annotated.csv");
    }

    @Override
    public void resetData() {
        metaDataManager.resetAll();
//        tagManager.resetAll();
    }

    @Override
    public Boolean getInitilisedStatus() {
        return systemInitialisedFlag;
    }

    public void setSystemInitialisedFlag(Boolean systemInitialisedFlag) {
        this.systemInitialisedFlag = systemInitialisedFlag;
    }

    public void deleteExperiementData(){
        plantManager.deleteAllPlants();
    }
}

