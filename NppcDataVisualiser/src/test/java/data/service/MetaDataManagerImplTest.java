package data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.doas.MetaDataDao;
import com.siongriffiths.nppcdatavisualiser.data.service.MetaDataManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class MetaDataManagerImplTest extends AbstractTest {

    @Autowired
    private MetaDataManager metaDataManager;

    @Autowired
    private MetaDataDao metaDataDao;

    @Autowired
    private ExperimentManager experimentManager;

    private Experiment testExperiment;

    @Before
    public void setUp(){
        testExperiment = experimentManager.getExperimentByCode("testCode");
    }
    @After
    public void tearDown(){}


    @Test
    public void findAll() throws Exception {
        List<Metadata> data = metaDataManager.findAll();
        assertNotNull("Expect list not null", data);
        assertFalse("Expect list not empty", data.isEmpty());
        assertEquals("Expect 20 results", 20 , data.size());
    }

    @Test
    public void findByExperiment() throws Exception {
        List<Metadata> data = metaDataManager.findByExperiment(testExperiment);
        assertNotNull("Expect list not null", data);
        assertFalse("Expect list not empty", data.isEmpty());
        assertEquals("Expect 17 results", 17 , data.size());
    }

    @Test @Transactional
    public void resetByExperiment() throws Exception {
        List<Metadata> data = metaDataManager.findByExperiment(testExperiment);
        assertNotNull("Expect list not null", data);
        assertFalse("Expect list not empty", data.isEmpty());
        assertEquals("Expect 17 results", 17 , data.size());

        String testKey = "testKey";
        String testValue = "testValue";

        for(Metadata m : data){
            m.addDataAttribute(testKey,testValue);
            metaDataManager.saveMetaData(m);
        }

        List<Metadata> resultSave = metaDataManager.findByExperiment(testExperiment);
        assertNotNull("Expect list not null", resultSave);
        assertFalse("Expect list not empty", resultSave.isEmpty());

        for(Metadata m : resultSave){
            String value = m.getDataAttributes().get(testKey);
            assertEquals("Expect results to have correct data values", value, testValue );
        }

        metaDataManager.resetByExperiment(testExperiment);
        List<Metadata> resultReset = metaDataManager.findByExperiment(testExperiment);
        assertNotNull("Expect list not null", resultReset);
        assertFalse("Expect list not empty", resultReset.isEmpty());

        for(Metadata m : resultReset){
            String value = m.getDataAttributes().get(testKey);
            assertNull("Expect value to be reset",value);
            assertTrue("Expect no attributes in metadata", m.getDataAttributes().isEmpty());
        }



    }

    @Test @Transactional
    public void saveMetaData() throws Exception {
        Integer id = 1;
        Metadata data = metaDataDao.findOne(id);
        assertNotNull("Expect data not null", data);
        assertTrue("Expect no attributes in data", data.getDataAttributes().isEmpty());

        String testKey = "testKey";
        String testValue = "testValue";

        data.addDataAttribute(testKey,testValue);
        metaDataManager.saveMetaData(data);

        Metadata result = metaDataDao.findOne(id);
        assertNotNull("Expect result not null", result);
        assertFalse("Expect attributes in data", result.getDataAttributes().isEmpty());
        assertEquals("Expect result has correct data", testValue, result.getDataAttributes().get(testKey));
    }

}