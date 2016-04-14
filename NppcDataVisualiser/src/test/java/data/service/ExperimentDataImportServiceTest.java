package data.service;

import com.siongriffiths.nppcdatavisualiser.data.service.ExperimentDataImportService;
import defaults.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class ExperimentDataImportServiceTest extends AbstractTest {


    @Autowired
    private ExperimentDataImportService experimentDataImportService;

    private String testFilePath = "I:\\Diss\\MajorProject\\NppcDataVisualiser\\src\\test\\java\\data\\service\\testImport.csv";

    @Test
    public void testParseAnnotatedExperimentDataCSVFile() throws Exception {

        experimentDataImportService.parseAnnotatedExperimentDataCSVFile(testFilePath);

    }

}
