package experiment;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.ExperimentStatus;
import com.siongriffiths.nppcdatavisualiser.experiment.daos.ExperimentDao;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created on 13/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
public class ExperimentManagerTest extends AbstractTest {

    @Autowired
    private ExperimentManager experimentManager;
    @Autowired
    private ExperimentDao experimentDao;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getAllExperiments() throws Exception {

        List<Experiment> experiments = experimentManager.getAllExperiments();
        assertNotNull("Expect experiments not null" , experiments);
        assertFalse("Expect experiments not empty" , experiments.isEmpty());
        assertEquals("Expect experiments size to be 4" ,4, experiments.size());

    }

    @Test
    public void getInitialisedExperiments() throws Exception {
        List<Experiment> initExperiemnts = experimentManager.getInitialisedExperiments();
        assertNotNull("Expect experiments not null" , initExperiemnts);
        assertEquals("Expect one initialised experiment" , initExperiemnts.size(), 1);

        Experiment experiment = experimentDao.findOne(1);
        experiment.setStatus(ExperimentStatus.INITIALISED);

        List<Experiment> result = experimentManager.getInitialisedExperiments();
        assertNotNull("Expect experiments not null" , result);
        assertFalse("Expect result to not be empty" , result.isEmpty());
        assertTrue("Expect experiment in results", result.contains(experiment));

    }

    @Test
    public void getExperimentByCode() throws Exception {
        String code = "experimentCode";
        Experiment experiment = experimentManager.getExperimentByCode(code);
        assertNotNull("Expect experiment not null", experiment);
        assertEquals("Expect that codes match",code, experiment.getExperimentCode());

    }

    @Test
    public void createNewExperiment() throws Exception {
        String code = "newcode";
        Experiment experiment = experimentManager.getExperimentByCode(code);
        assertNull("Expect experiment is null", experiment);

        Experiment newExperiment = experimentManager.createNewExperiment(code);
        assertNotNull("Expect experiment not null", newExperiment);
        assertEquals("Expect that codes match",ExperimentStatus.NOT_INITIALISED, newExperiment.getStatus());
    }

    @Test
    public void getOrCreateNewExperiment() throws Exception {
        Integer id = 1;
        Experiment experiment = experimentDao.findOne(id);
        String code = experiment.getExperimentCode();

        assertEquals("Expect experiments size to be 4" ,4, experimentManager.getAllExperiments().size());

        Experiment inDb = experimentManager.getOrCreateNewExperiment(code);
        assertNotNull("Expect experiment inDb not null", inDb);
        assertEquals("Expect that codes match",id, inDb.getId());
        experimentManager.saveExperiment(inDb);
        assertEquals("Expect experiments size to be 4" ,4, experimentManager.getAllExperiments().size());

        String notinDbCode = "notInDb";
        Experiment newExperiment = experimentManager.getOrCreateNewExperiment(notinDbCode);
        assertNotNull("Expect experiment inDb not null", inDb);
        assertFalse("Expect newExperiment not in list",experimentManager.getAllExperiments().contains(newExperiment));
        experimentManager.saveExperiment(newExperiment);
        assertTrue("Expect newExperiment in list",experimentManager.getAllExperiments().contains(newExperiment));

    }

    @Test
    public void getExperimentStatus() throws Exception {
        Integer id = 1;
        Experiment experiment = experimentDao.findOne(id);
        ExperimentStatus status = experiment.getStatus();

        ExperimentStatus resultStatus = experimentManager.getExperimentStatus(experiment.getExperimentCode());
        assertEquals("Expect statuses are equal", status,resultStatus);
    }

    @Test
    public void updateStatus() throws Exception {
        Integer id = 1;
        Experiment experiment = experimentDao.findOne(id);
        ExperimentStatus status = experiment.getStatus();
        assertNotNull("Expect not null status",status);
        assertEquals("Expect statuses are equal", ExperimentStatus.INITIALISED, status);

        experimentManager.updateStatus(experiment,ExperimentStatus.NOT_INITIALISED);
        assertNotNull("Expect not null status",experiment.getStatus());
        assertEquals("Expect statuses are equal", ExperimentStatus.NOT_INITIALISED,experiment.getStatus());
    }

    @Test
    public void addPlantToExperiment() throws Exception {
        Integer id = 3;
        Experiment experiment = experimentDao.findOne(id);
        List<Plant> plants = experiment.getPlants();
        assertNotNull("Expect plant list is not null" , plants);
        assertTrue("Expect plant list empty", plants.isEmpty());

        Plant plant = new Plant("barcode");
        experimentManager.addPlantToExperiment(experiment,plant);
        List<Plant> results = experiment.getPlants();
        assertNotNull("Expect result list is not null" , results);
        assertFalse("Expect result list is not empty", plants.isEmpty());
        assertTrue("Expect result contains plant", results.contains(plant));

    }

    @Test
    public void saveExperiment() throws Exception {
        Integer id = 3;
        Experiment experiment = experimentDao.findOne(id);
        String code = experiment.getExperimentCode();

        String newCode = "newCode";

        assertNotEquals("Expect codes are different", code, newCode);

        experiment.setExperimentCode(newCode);
        experimentManager.saveExperiment(experiment);

        Experiment result = experimentDao.findOne(id);
        assertEquals("Expect result code matches new code", result.getExperimentCode(), newCode);


    }

}