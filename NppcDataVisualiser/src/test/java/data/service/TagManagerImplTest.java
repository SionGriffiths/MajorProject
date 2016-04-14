package data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import defaults.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Transactional
public class TagManagerImplTest extends AbstractTest {



    @Autowired
    private TagManager tagManager;
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
    public void findByID() throws Exception {
        Long id = 1L;
        Long id2 = 100L;

        TagData tag = tagManager.findByID(id);
        assertNotNull("Expect tag not null",tag);
        assertEquals("Expect ids to match",id,tag.getId(),0.001);

        TagData tag2 = tagManager.findByID(id2);
        assertNull("Expect tag2 to be null",tag2);

    }

    @Test
    public void saveTagData() throws Exception {
        Long id = 1L;
        TagData tag = tagManager.findByID(id);
        assertNotNull("Expect tag not null",tag);
        assertNotNull("Expect content not null", tag.getTagContent());
        String content = tag.getTagContent();

        String newContent = "new content";
        assertNotEquals("Expect content to differ",content,newContent);
        tag.setTagContent(newContent);

        tagManager.saveTagData(tag);
        TagData result = tagManager.findByID(id);
        assertNotNull("Expect result not null",result);
        assertEquals("Expect result has new content",newContent,result.getTagContent());
    }

    @Test
    public void getTagByContent() throws Exception {
        Long id = 1L;
        String content = "Test content";
        TagData tag = tagManager.findByID(id);
        assertNotNull("Expect tag not null",tag);
        tag.setTagContent(content);
        tagManager.saveTagData(tag);

        TagData result = tagManager.getTagByContent(content);
        assertNotNull("Expect tag not null",tag);
        assertEquals("Expect content the same", content,result.getTagContent());
        assertEquals("Expect ids to match",result.getId(),tag.getId(),0.001);
    }

    @Test
    public void getAllTags() throws Exception {
        List<TagData> tags = tagManager.getAllTags();
        assertNotNull("Expect not null list", tags);
        assertFalse("Expect list not empty", tags.isEmpty());
        assertEquals("Expect 5 results in list", 5, tags.size());
    }

    @Test
    public void getByExperimentForPlants() throws Exception {
        Set<TagData> tags = tagManager.getByExperimentForPlants(testExperiment);
        assertNotNull("Expect not null collection", tags);
        assertFalse("Expect collection not empty", tags.isEmpty());
        assertEquals("Expect 3 results in collection", 3, tags.size());
    }

    @Test
    public void getByExperimentForPlantDays() throws Exception {
        Set<TagData> tags = tagManager.getByExperimentForPlantDays(testExperiment);
        assertNotNull("Expect not null collection", tags);
        assertFalse("Expect collection not empty", tags.isEmpty());
        assertEquals("Expect 2 results in collection", 2, tags.size());
    }

}