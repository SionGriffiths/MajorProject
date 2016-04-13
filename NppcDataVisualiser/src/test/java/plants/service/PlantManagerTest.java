package plants.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Transactional
public class PlantManagerTest extends AbstractTest {


    @Autowired
    private PlantManager plantManager;

    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testSavePlant(){
        Long id = 1L;
        Plant plant = plantManager.getPlantByID(id);
        assertNotNull("Expected null result",plantManager.getPlantByID(id));

        String originalBarcode = plant.getBarCode();
        String newBarcode = "newBarcode";
        plant.setBarCode(newBarcode);
        assertNotEquals("Expected bar codes to be different", originalBarcode, plant.getBarCode());

        plantManager.savePlant(plant);

        Plant result = plantManager.getPlantByID(id);
        assertEquals("Expected result barcode to be equal to new barcode ", result.getBarCode(), newBarcode);

    }

    @Test
    public void testDeleteByExperiment(){
        String experimentCode = "testCode";
        List<Plant> plantsByExperimentCode = plantManager.findPlantsByExperimentCode(experimentCode);
        assertNotNull("Expected list not to be null", plantsByExperimentCode);
        assertEquals("Expected list size to be 10", 10,plantsByExperimentCode.size());

        Experiment experiment = plantsByExperimentCode.get(0).getExperiment();
        assertNotNull("Expected experiment not to be null",experiment);

        plantManager.deleteByExperiment(experiment);

        assertEquals("Expect no results", 0, plantManager.findPlantsByExperimentCode(experimentCode).size());
    }

    @Test
    public void testGetPlantByBarcode(){
        String barcode = "bc1";
        Plant plant = plantManager.getPlantByBarcode(barcode);
        assertNotNull("Plant should not be null", plant);
        assertEquals("Bar codes should match", plant.getBarCode(),barcode);
    }

    @Test
    public void testCreateOrGetPlantByBarcode(){
        String barcode = "notInDb";

        String inDbBarcode = "bc1";

        Plant foundPlant = plantManager.getPlantByBarcode(inDbBarcode);
        assertNotNull("Expect not plant", foundPlant);

        Plant plant = plantManager.getPlantByBarcode(barcode);
        assertNull("Expect null plant", plant);

        Plant createdPlant = plantManager.createOrGetPlantByBarcode(barcode);
        assertNotNull("Expected created plant not to be null", createdPlant);
        assertNotNull("Expected created plant to have not null id", createdPlant.getId());
        assertNotNull("Expected created plant to have not null metadata", createdPlant.getMetadata());
        assertNotNull("Expected created plant to have not null tags", createdPlant.getTags());

        Plant retrievedPlant = plantManager.createOrGetPlantByBarcode(inDbBarcode);
        assertNotNull("Expect plant not null", retrievedPlant);
        assertEquals("expect similar IDs", retrievedPlant.getId(), foundPlant.getId());
    }





    @Test
    public void testTagPlant(){
        Long id = 1l;
        Plant plant = plantManager.getPlantByID(id);
        assertNotNull("Plant should not be null", plant);

        assertTrue("Plant should have no tags", plant.getTags().isEmpty());

        TagData tag = new TagData("tagContent");
        plantManager.tagPlant(tag,plant);

        assertFalse("Plant should have tags", plant.getTags().isEmpty());
        assertEquals("PLant should have 1 tag",1,plant.getTags().size());
    }

    @Test
    public void findPlantsByTagForExperiment() throws Exception {
        Long id = 10L;
        Plant plant = plantManager.getPlantByID(id);

        TagData tag = plant.getTags().iterator().next();
        assertNotNull("Expect TagData not null", tag);
        Experiment experiment = plant.getExperiment();
        assertNotNull("Expect experiment not null", experiment);

        List<Plant> results = plantManager.findPlantsByTagForExperiment(tag,experiment);
        assertNotNull("Expect results not null", results);
        assertFalse("Expect results to have plants" ,results.isEmpty());
        assertTrue("Expect original plant in results",results.contains(plant));
    }

    @Test
    public void testFindByExperimentCode(){

        List<Plant> plantsByExperimentCode = plantManager.findPlantsByExperimentCode("testCode");
        assertNotNull("Expected list not to be null", plantsByExperimentCode);
        assertEquals("Expected list size to be 10", 10 ,plantsByExperimentCode.size());

    }

    @Test
    public void findPlantsByExperimentCodePageable() {

        PageRequest pageRequest = new PageRequest(1, 2);

        Page<Plant> plantsPage = plantManager.findPlantsByExperimentCode("testCode", pageRequest);
        assertNotNull("Expected list not to be null", plantsPage);
        assertEquals("Expected list size to be 2", 2 ,plantsPage.getContent().size());

        PageRequest secondPageRequest = new PageRequest(2, 2);
        Page<Plant> secondPlantsPage = plantManager.findPlantsByExperimentCode("testCode", secondPageRequest);
        assertNotNull("Expected list not to be null", secondPlantsPage);
        assertEquals("Expected list size to be 2", 2 ,secondPlantsPage.getContent().size());

        assertFalse("Expect different plants in pages",plantsPage.getContent().containsAll(secondPlantsPage.getContent()));
    }

    @Test
    public void getPlantByID()  {
        Long id = 01L;
        Plant plant = plantManager.getPlantByID(id);
        assertNotNull("Plant should not be null", plant);


    }

    @Test
    public void getPlantByIDString()  {
        String id = "01";
        Plant plant = plantManager.getPlantByID(id);
        assertNotNull("Plant should not be null", plant);
    }

    @Test
    public void resetTagsForExperiment()  {
        Long id = 10L;
        Plant plant = plantManager.getPlantByID(id);
        Experiment experiment = plant.getExperiment();

        assertEquals("Expected number of tags to be 2", 2 , plant.getTags().size());

        plantManager.resetTagsForExperiment(experiment);
        assertEquals("Expected number of tags to be 0", 0 , plant.getTags().size());
    }

    @Test
    public void findPlantsWithTagsByExperiment()  {
        Long id = 01L;
        Plant plant = plantManager.getPlantByID(id);
        Experiment experiment = plant.getExperiment();

        List<Plant> plants = plantManager.findPlantsWithTagsByExperiment(experiment);
        assertNotNull("Expected list not to be null", plants);
        assertEquals("Expected 2 results",2,plants.size());
    }

    @Test
    public void findByTwoAttrributeValues()  {
        Plant plant = new Plant("barcode");
        Metadata data = plant.getMetadata();
        assertNotNull("ExpectMetadata not null", data);
        data.addDataAttribute("key1","value1");
        data.addDataAttribute("key2","value2");
        plantManager.savePlant(plant);

        List<Plant> results = plantManager.findByTwoAttrributeValues("key1","value1","key2","value2");
        assertFalse("Expected results not empty", results.isEmpty());
        assertTrue("Expect results contain original plant", results.contains(plant));

    }





}
