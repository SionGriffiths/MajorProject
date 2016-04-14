package data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.ExperimentDataImportService;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import defaults.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class ExperimentDataImportServiceTest extends AbstractTest {


    @Autowired
    private ExperimentDataImportService experimentDataImportService;
    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantDayManager plantDayManager;

    private String testFilePath = "src/test/resources/testImport.csv";


    @Test @Transactional
    public void testParseAnnotatedExperimentDataCSVFile() throws Exception {

        Plant plant = plantManager.getPlantByBarcode("bc1");
        assertNotNull("Expect plant bc1 not null", plant);

        Metadata data = plant.getMetadata();
        assertNotNull("Expect data not null", data);
        assertTrue("Expect empty plant data",data.getDataAttributes().isEmpty());

        Set<TagData> tags = plant.getTags();
        assertNotNull("Expect tags not null", tags);
        assertTrue("Expect tags data",tags.isEmpty());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2016-02-02");
        PlantDay day = plantDayManager.findByPlantAndDate(plant, date);
        assertNotNull("Expect day not null", day);
        assertTrue("Expect empty day metadata",day.getMetadata().getDataAttributes().isEmpty());


        experimentDataImportService.parseAnnotatedExperimentDataCSVFile(testFilePath);

        assertFalse("Expect not empty plant data",data.getDataAttributes().isEmpty());
        assertFalse("Expect has tags data",tags.isEmpty());
        assertFalse("Expect not empty day metadata",day.getMetadata().getDataAttributes().isEmpty());
    }



}
