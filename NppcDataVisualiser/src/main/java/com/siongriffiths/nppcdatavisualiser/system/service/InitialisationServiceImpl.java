package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.data.service.ExperimentDataImportService;
import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.imageutils.PlantLoader;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
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
@Service("initialisationService")
public class InitialisationServiceImpl implements InitialisationService {

    private Boolean systemInitialisedFlag = Boolean.FALSE;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PlantLoader plantLoader;
    @Autowired
    private ExperimentDataImportService experimentDataImportService;
    @Autowired
    private MetaDataManager metaDataManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantDayManager plantDayManager;
    @Autowired
    private ExperimentManager experimentManager;

    /**
     * Property value found in default property file.
     * Contains the root directory for experiment data files
     */
    @Value("${experiment.data.root.dir}")
    private String dataRoot;

    public void initExperiment(String experimentCode){
        Experiment experiment = getExperimentForCode(experimentCode);
        experimentManager.updateStatus(experiment,ExperimentStatus.INITIALISING);
        plantLoader.initPlantImages(experiment);
    }

    public void initData(String experimentCode){
        experimentDataImportService.parseAnnotatedExperimentDataCSVFile(dataRoot + experimentCode+"/annotated.csv");
    }

    @Override
    public void resetData(String experimentCode) {
        metaDataManager.resetByExperiment(getExperimentForCode(experimentCode));
        resetTagsForExperiment(getExperimentForCode(experimentCode));
    }

    public void deleteExperiementData(String experimentCode){
        Experiment experiment = getExperimentForCode(experimentCode);
        experimentManager.updateStatus(experiment, ExperimentStatus.DELETING);
        plantManager.deleteByExperiment(experiment);
        experimentManager.updateStatus(experiment, ExperimentStatus.NOT_INITIALISED);
    }

    private Experiment getExperimentForCode(String experimentCode){
        return experimentManager.getOrCreateNewExperiment(experimentCode) ;
    }

    private void resetTagsForExperiment(Experiment experiment){
        plantManager.resetTagsForExperiment(experiment);
        plantDayManager.resetTagsForExperiment(experiment);
    }
}

