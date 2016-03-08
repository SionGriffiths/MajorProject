package plants.daos;

import com.siongriffiths.nppcdatavisualiser.Application;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created on 29/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes ={Application.class} )
public class PlantDaoTest {

    @Autowired
    PlantDao plantDao;

    @Test
    public void testSavePlant(){

        String initialBarCode = "90210";
        String secondaryBarCode = "13243";

        Plant plant = new Plant();
        plant.setBarCode(initialBarCode);
        plantDao.savePlant(plant);

        Plant retrievedPlant = plantDao.getPlantByBarcode(initialBarCode);

        assertNotNull(retrievedPlant);
        assertEquals(retrievedPlant.getBarCode(),initialBarCode);

        retrievedPlant.setBarCode(secondaryBarCode);
        plantDao.savePlant(retrievedPlant);
    }

}
