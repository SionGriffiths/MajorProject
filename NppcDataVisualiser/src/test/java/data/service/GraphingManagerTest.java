package data.service;

import com.siongriffiths.nppcdatavisualiser.data.service.GraphingManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import defaults.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class GraphingManagerTest extends AbstractTest {



    @Autowired
    private GraphingManager graphingManager;
    @Autowired
    private PlantManager plantManager;

    @Test @Transactional
    public void testGetGraphDataForPlant() throws Exception {

        Plant plant = plantManager.getPlantByBarcode("bc1");
        String testAttributeKey = "attr1";

        Map<String, List<String>> graphData = graphingManager.getGraphDataForPlant(plant,testAttributeKey,"");
        assertNotNull("Expect graphData not null",graphData);
        assertFalse("Expect graphData not empty",graphData.isEmpty());

        List<String > attr1Values = graphData.get("y");
        assertNotNull("Expect attr1Values not null",attr1Values);
        assertFalse("Expect attr1Values not empty",attr1Values.isEmpty());
        assertEquals("Expect 2 results for arrt1",2,attr1Values.size());

        List<String > dateValues = graphData.get("x");
        assertNotNull("Expect dateValues not null",dateValues);
        assertFalse("Expect dateValues not empty",dateValues.isEmpty());
        assertEquals("Expect 2 results for dateValues",2,dateValues.size());
    }

    @Test @Transactional
    public void testGetGraphData() throws Exception {
        String testAttr1 = "test";
        String testAttr2 = "test2";
        String experimentCode = "testCode";

        Map<String, List<String>> graphData = graphingManager.getGraphData(testAttr1, testAttr2, experimentCode);
        assertNotNull("Expect graphData not null",graphData);
        assertFalse("Expect graphData not empty",graphData.isEmpty());

        List<String > attr1Values = graphData.get("y");
        assertNotNull("Expect attr1Values not null",attr1Values);
        assertFalse("Expect attr1Values not empty",attr1Values.isEmpty());
        assertEquals("Expect 3 results for attr1Values",3,attr1Values.size());

        List<String > attr2Values = graphData.get("x");
        assertNotNull("Expect attr2Values not null",attr2Values);
        assertFalse("Expect attr2Values not empty",attr2Values.isEmpty());
        assertEquals("Expect 3 results for attr2Values",3,attr2Values.size());
    }

}
