package controllers;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.experiment.service.ExperimentManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import defaults.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created on 01/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class PlantPageControllerTest  extends AbstractTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private PlantManager plantManager;
    @Autowired
    private PlantDayManager plantDayManager;
    @Autowired
    private ExperimentManager experimentManager;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testShowPlants() throws Exception {
        this.mockMvc.perform(get("/plants")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Plants Page")));
    }


    @Test
    public void testPlant() throws Exception {
        String testBarCode = "55345";
        Experiment experiment = new Experiment("TEST");
        Plant plant = new Plant();
        plant.setBarCode(testBarCode);
        experimentManager.saveExperiment(experiment);
        plant.setExperiment(experiment);
        plantManager.savePlant(plant);

        //http://stackoverflow.com/questions/26341400/mvc-controller-test-with-session-attribute/26341909#26341909
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
sessionattr.put("experimentCode",experiment.getExperimentCode());

        this.mockMvc.perform(get("/plants").sessionAttrs(sessionattr)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(testBarCode)));

        String plantUrl = "/plants/"+testBarCode;
        this.mockMvc.perform(get(plantUrl).sessionAttrs(sessionattr)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the detail page for "+testBarCode)));

        String notPlantUrl = "/plants/123456";
        this.mockMvc.perform(get(notPlantUrl).sessionAttrs(sessionattr)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Plant not Found")));

    }

    @Test
    public void testTagPlantDay() throws Exception{
        PlantDay day = new PlantDay();
        Plant plant = new Plant();
        day.setPlant(plant);
        plantManager.savePlant(plant);
        plantDayManager.savePlantDay(day);

        String id = Long.toString(day.getId());
        String tag = "testTagContent";

        this.mockMvc.perform(post("/plants/addDayTag")
                .param("tagContent",tag)
                .param("plantDayID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(tag)));


    }

    @Test
    public void testAddAttribToPlantDay() throws Exception{
        PlantDay day = new PlantDay();
        Plant plant = new Plant();
        day.setPlant(plant);
        day.setMetadata(new Metadata());
        plantManager.savePlant(plant);
        plantDayManager.savePlantDay(day);

        String id = Long.toString(day.getId());
        String aName = "testAttributeName";
        String aValue = "testAttributeValue";


        this.mockMvc.perform(post("/plants/addDayAttribute")
                .param("attribName",aName)
                .param("attribVal",aValue)
                .param("plantDayID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(aValue)))
                .andExpect(content().string(containsString(aName)));

    }

}
