package plants.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created on 13/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
public class PlantDayManagerTest extends AbstractTest {

    @Autowired
    private PlantDayManager plantDayManager;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void savePlantDay() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        assertNotNull(day);

        Date date = day.getDate();
        Date newDate = new Date();

        day.setDate(newDate);
        assertNotEquals("Expected dates to be different", clearMillis(day.getDate()), clearMillis(date));

        plantDayManager.savePlantDay(day);

        PlantDay result = plantDayManager.getPlantDayByID(id);
        assertEquals("Expect dates are the same", clearMillis(newDate) , clearMillis(result.getDate() ));
    }

    //clear out milliseconds to enable reasonable equality comparison for test purposes
    private Date clearMillis(Date date){
        long time = date.getTime();
        date.setTime((time / 1000) * 1000);
        return date;
    }

    @Test
    public void tagPlantDay() throws Exception {
        Long id = 2L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        assertNotNull("PlantDay should not be null", day);
        assertTrue("PlantDay should have no tags", day.getTags().isEmpty());

        TagData tag = new TagData("tagContent");
        plantDayManager.tagPlantDay(tag,day);

        assertFalse("PlantDay should have tags", day.getTags().isEmpty());
        assertEquals("PlantDay should have 1 tag",1,day.getTags().size());
    }

    @Test
    public void getPlantDayByID() throws Exception {
        PlantDay day = plantDayManager.getPlantDayByID(1);
        assertNotNull("Expect day not null", day);
    }

    @Test
    public void getPlantDayByIDString() throws Exception {
        PlantDay day = plantDayManager.getPlantDayByID("1");
        assertNotNull("Expect day not null", day);
    }

    @Test
    public void findByTagDataForExperiment() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);

        TagData tag = day.getTags().iterator().next();
        assertNotNull("Expect TagData not null", tag);
        Experiment experiment = day.getPlant().getExperiment();
        assertNotNull("Expect experiment not null", experiment);

        List<PlantDay> results = plantDayManager.findByTagDataForExperiment(tag,experiment);
        assertNotNull("Expect results not null", results);
        assertFalse("Expect results to have plants" ,results.isEmpty());
        assertTrue("Expect original plant in results",results.contains(day));
    }

    @Test
    public void findByPlantAndDate() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        Date date = day.getDate();
        Plant plant = day.getPlant();

        PlantDay result = plantDayManager.findByPlantAndDate(plant, date);
        assertNotNull("Expect results not null", result);
        assertEquals("Expect same ID", day.getId(), result.getId());
    }

    @Test
    public void findPlantDaysByExperiment() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);

        Experiment experiment = day.getPlant().getExperiment();

        List<PlantDay> days = plantDayManager.findPlantDaysByExperiment(experiment);
        assertNotNull("Expected list not to be null", days);
        assertEquals("Expected list size to be 7", 7 ,days.size());
    }

    @Test
    public void getPlantDaysByPlant() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        Plant plant = day.getPlant();

        PageRequest pageRequest = new PageRequest(1, 2);

        Page<PlantDay> plantsPage = plantDayManager.getPlantDaysByPlant(plant, pageRequest);
        assertNotNull("Expected list not to be null", plantsPage);
        assertEquals("Expected list size to be 2", 2 ,plantsPage.getContent().size());

        PageRequest secondPageRequest = new PageRequest(2, 2);
        Page<PlantDay> secondPlantsPage = plantDayManager.getPlantDaysByPlant(plant, secondPageRequest);
        assertNotNull("Expected list not to be null", secondPlantsPage);
        assertEquals("Expected list size to be 2", 2 ,secondPlantsPage.getContent().size());

        assertFalse("Expect different plants in pages",plantsPage.getContent().containsAll(secondPlantsPage.getContent()));

    }

    @Test
    public void resetTagsForExperiment() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        Plant plant = day.getPlant();
        Experiment experiment = plant.getExperiment();

        assertEquals("Expected number of tags to be 2", 2 , day.getTags().size());

        plantDayManager.resetTagsForExperiment(experiment);
        assertEquals("Expected number of tags to be 0", 0 , day.getTags().size());
    }

    @Test
    public void findPlantDaysWithTagsByExperiment() throws Exception {
        Long id = 1L;
        PlantDay day = plantDayManager.getPlantDayByID(id);
        Plant plant = day.getPlant();
        Experiment experiment = plant.getExperiment();

        List<PlantDay> days = plantDayManager.findPlantDaysWithTagsByExperiment(experiment);
        assertNotNull("Expected list not to be null", days);
        assertEquals("Expected 2 results",2,days.size());
    }

}
