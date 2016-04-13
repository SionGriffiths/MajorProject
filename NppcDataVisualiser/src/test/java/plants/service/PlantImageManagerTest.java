package plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantImageDao;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantImageManager;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created on 14/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class PlantImageManagerTest extends AbstractTest{

    @Autowired
    private PlantImageManager plantImageManager;
    @Autowired
    private PlantImageDao plantImageDao;

    @Before
    public void setUp(){}
    @After
    public void tearDown(){}

    @Test
    public void testGetOrCreatePlantImageByPath(){
        String path = "testFilePath";
        PlantImage image = new PlantImage(path);
        plantImageDao.save(image);


        PlantImage imageNew = plantImageManager.getOrCreatePlantImageByPath("newPath");
        assertNotNull("Expect image not null", imageNew);
        PlantImage retrievedImage = plantImageManager.getOrCreatePlantImageByPath(path);
        assertEquals("Expect paths are the same", image.getFilePath(), retrievedImage.getFilePath());
        assertEquals("Expect IDs are the same", image.getId(), retrievedImage.getId());
    }

}
