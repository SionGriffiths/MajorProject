package com.siongriffiths.nppcdatavisualiser.system.service;

import com.siongriffiths.nppcdatavisualiser.data.service.ExperimentDataImportService;
import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.imageutils.PlantLoader;
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
 *
 * InitialisationServiceImpl implements all business logic and behaviour as defined in the InitialisationService interface.
 */
@Service("initialisationService")
public class InitialisationServiceImpl implements InitialisationService {

    /**
     * Logger instance for this class
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The PlantLoader instance, provides access to domain object creation
     */
    @Autowired
    private PlantLoader plantLoader;

    /**
     * ExperimentDataImportService, provides business processes associated with experiment data
     */
    @Autowired
    private ExperimentDataImportService experimentDataImportService;

    /**
     * MetaDataManager, a service class providing access to business logic and persistence for Metadata objects
     */
    @Autowired
    private MetaDataManager metaDataManager;
    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;

    /**
     * PlantDayManager, a service class providing access to business logic and persistence for PlantDay objects
     */
    @Autowired
    private PlantDayManager plantDayManager;

    /**
     * ExperimentManager, a service class providing access to business logic and persistence for Experiment objects
     */
    @Autowired
    private ExperimentManager experimentManager;

    /**
     * Property value found in default property file.
     * Contains the root directory for experiment data files
     */
    @Value("${experiment.data.root.dir}")
    private String dataRoot;

    /**
     * Initialises an Experiment, invokes the creation of domain model objects
     * @param experimentCode the code for the experiment
     */
    public void initExperiment(String experimentCode){
        Experiment experiment = getExperimentForCode(experimentCode);
        experimentManager.updateStatus(experiment,ExperimentStatus.INITIALISING);
        plantLoader.initExperiment(experiment);
    }

    /**
     * Loads data for an experiment
     * @param experimentCode the code for the experiment
     */
    public void initData(String experimentCode){
        experimentDataImportService.parseAnnotatedExperimentDataCSVFile(dataRoot + experimentCode+"/annotated.csv");
    }

    /**
     * Resets experiment data
     * @param experimentCode the code for the experiment
     */
    @Override
    public void resetData(String experimentCode) {
        metaDataManager.resetByExperiment(getExperimentForCode(experimentCode));
        resetTagsForExperiment(getExperimentForCode(experimentCode));
    }

    /**
     * Deletes experiment
     * @param experimentCode the code for the experiment
     */
    public void deleteExperiement(String experimentCode){
        Experiment experiment = getExperimentForCode(experimentCode);
        experimentManager.updateStatus(experiment, ExperimentStatus.DELETING);
        plantManager.deleteByExperiment(experiment);
        experimentManager.updateStatus(experiment, ExperimentStatus.NOT_INITIALISED);
    }

    /**
     * Retrieves an experiment for a given code
     * @param experimentCode the code for the experiment
     * @return the retrieved experiment
     */
    private Experiment getExperimentForCode(String experimentCode){
        return experimentManager.getOrCreateNewExperiment(experimentCode) ;
    }

    /**
     * Resets tags associated with an experiment
     * @param experiment the experiment
     */
    private void resetTagsForExperiment(Experiment experiment){
        plantManager.resetTagsForExperiment(experiment);
        plantDayManager.resetTagsForExperiment(experiment);
    }
}

