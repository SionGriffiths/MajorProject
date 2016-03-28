package data.dao;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.doas.MetaDataDao;
import defaults.AbstractTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
public class MetaDataDaoTest extends AbstractTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MetaDataDao metaDataDao;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void testTheDao(){

        Metadata data = new Metadata();
        metaDataDao.save(data);
        Integer orgId = data.getId();
        logger.info("\n \n  " + orgId);

        String testGrowthStage = "2";
        data.addDataAttribute("testKey", "22");
        metaDataDao.save(data);
        data.getDataAttributes();
        List<Metadata> retrievedByKey =  metaDataDao.findByDataAttributeKey("testKey");

        Metadata retrievedData = metaDataDao.findOne(orgId);
//        assertEquals(retrievedData.getGrowthStage(),testGrowthStage);

    }

}
