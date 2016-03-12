package plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import defaults.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created on 29/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
public class PlantDaoTest extends AbstractTest {

    @Autowired
    PlantDao plantDao;

    @Test
    public void testSavePlant(){

        String initialBarCode = "90210";
        String secondaryBarCode = "13243";

        Plant plant = new Plant();
        plant.setBarCode(initialBarCode);
        plantDao.save(plant);

        Plant retrievedPlant = plantDao.findByBarCode(initialBarCode);

        assertNotNull(retrievedPlant);
        assertEquals(retrievedPlant.getBarCode(),initialBarCode);

        retrievedPlant.setBarCode(secondaryBarCode);
        plantDao.save(retrievedPlant);
    }

}
