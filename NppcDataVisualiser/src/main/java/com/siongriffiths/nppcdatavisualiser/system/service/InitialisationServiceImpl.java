package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.data.service.ExperiemntDataImportService;
import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.imageutils.PlantLoader;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private PlantLoader plantLoader;
    @Autowired
    private ExperiemntDataImportService experiemntDataImportService;
    @Autowired
    private MetaDataManager metaDataManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private PlantManager plantManager;
    @Autowired
    private ExperimentManager experimentManager;

    /**
     * Property value found in default property file.
     * Contains the root directory for experiemnt data files
     */
    @Value("${experiment.data.root.dir}")
    private String dataRoot;

    public void initExperiment(String experimentCode){
        Experiment experiment = experimentManager.getOrCreateNewExperiment(experimentCode) ;
        experiment.setStatus(ExperimentStatus.INITIALISING);
        plantLoader.initPlantImages(experiment);
   }

    public void initData(String experimentCode){
        experiemntDataImportService.parseAnnotatedExperiemntDataCSVFile(dataRoot + experimentCode+"/annotated.csv");
    }

    @Override
    public void resetData(String experimentCode) {
        Experiment experiment = experimentManager.getOrCreateNewExperiment(experimentCode) ;
        metaDataManager.resetByExperiment(experiment);
//        tagManager.resetAll();
        // TODO: 04/04/2016 Reset tags!
    }

    public void deleteExperiementData(String experimentCode){
        plantManager.deleteAllPlants();
    }
}

