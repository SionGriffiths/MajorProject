package controllers;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    //http://stackoverflow.com/questions/26341400/mvc-controller-test-with-session-attribute/26341909#26341909
    private HashMap<String, Object> sessionattr;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        sessionattr = new HashMap<>();
        sessionattr.put("experimentCode","testCode");
    }

    @Test
    public void testShowPlantsNoExperiment() throws Exception {
        this.mockMvc.perform(get("/plants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("There are 0 plants in this experiment ")))
                .andExpect(view().name("plants/show"));
    }


    @Test
    public void testShowPlants() throws Exception {

        String testBarCode = "bc1";

        this.mockMvc.perform(get("/plants").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(testBarCode)))
                .andExpect(view().name("plants/show"));
    }

    @Test
    public void testShowPlantDetail() throws Exception {

        String testBarCode = "bc2";

        String plantUrl = "/plants/"+testBarCode;
        this.mockMvc.perform(get(plantUrl).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the detail page for "+testBarCode)))
                .andExpect(view().name("plants/plantdetail"));
    }

    @Test
    public void testPlantNotFound() throws Exception {
        String notInSystemBarcode = "barcode does not in system";
        String notPlantUrl = "/plants/"+notInSystemBarcode;
        this.mockMvc.perform(get(notPlantUrl).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Plant not Found")))
                .andExpect(content().string(containsString("Could not find plant with barcode "+notInSystemBarcode)))
                .andExpect(view().name("plants/notfound"));
    }


    @Test
    public void testPlantPagePaginationPage() throws Exception {

        this.mockMvc.perform(get("/plants")
                .param("page","1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(view().name("plants/show"));

        this.mockMvc.perform(get("/plants")
                .param("page","2")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",3))
                .andExpect(model().attribute("currentPage",2))
                .andExpect(model().attribute("prevPage",1))
                .andExpect(view().name("plants/show"));

        this.mockMvc.perform(get("/plants")
                .param("page","-1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(view().name("plants/show"));
    }


    @Test
    public void testPlantPagePaginationPageSize() throws Exception {

        this.mockMvc.perform(get("/plants")
                .param("page","1")
                .param("size","1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",1))
                .andExpect(view().name("plants/show"));

        this.mockMvc.perform(get("/plants")
                .param("page","1")
                .param("size","3")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",3))
                .andExpect(view().name("plants/show"));

        this.mockMvc.perform(get("/plants")
                .param("page","1")
                .param("size","-1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",1))
                .andExpect(view().name("plants/show"));


    }


    @Test
    public void testPlantDetailsPagePaginationPage() throws Exception {

        String plantCode = "bc1";

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(view().name("plants/plantdetail"));

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","2")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",3))
                .andExpect(model().attribute("currentPage",2))
                .andExpect(model().attribute("prevPage",1))
                .andExpect(view().name("plants/plantdetail"));

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","-1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(view().name("plants/plantdetail"));
    }


    @Test
    public void testPlantDetailsPagePaginationPageSize() throws Exception {

        String plantCode = "bc1";

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","1")
                .param("size","1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",1))
                .andExpect(view().name("plants/plantdetail"));

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","1")
                .param("size","3")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("nextPage",2))
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",3))
                .andExpect(view().name("plants/plantdetail"));

        this.mockMvc.perform(get("/plants/"+plantCode)
                .param("page","1")
                .param("size","-1")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("currentPage",1))
                .andExpect(model().attribute("currentSize",1))
                .andExpect(view().name("plants/plantdetail"));


    }

    @Test
    public void testBadPaginationParams() throws Exception {
        this.mockMvc.perform(get("/plants/")
                .param("page","bad param")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"));
    }

    @Test @Transactional
    public void testTagPlant() throws Exception {

        Plant plant = plantManager.getPlantByID(2);
        String id = Long.toString(plant.getId());
        String tag = "content 1";

        this.mockMvc.perform(post("/plants/addPlantTag")
                .param("tagContent",tag)
                .param("plantID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plant"))
                .andExpect(content().string(containsString(tag)))
                .andExpect(view().name("plants/plantFragments :: plantTagFragment"));


    }

    @Test @Transactional
    public void testAddPlantAttribute() throws Exception {
        Plant plant = plantManager.getPlantByID(2);
        String id = Long.toString(plant.getId());
        String aName = "testAttributeName";
        String aValue = "testAttributeValue";


        this.mockMvc.perform(post("/plants/addPlantAttribute")
                .param("attribName",aName)
                .param("attribVal",aValue)
                .param("plantID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plant"))
                .andExpect(content().string(containsString(aValue)))
                .andExpect(content().string(containsString(aName)))
                .andExpect(view().name("plants/plantFragments :: plantAttribFragment"));
    }


    @Test @Transactional
    public void testTagPlantDay() throws Exception {

        PlantDay day = plantDayManager.getPlantDayByID(1);
        String id = Long.toString(day.getId());
        String tag = "content 5";

        this.mockMvc.perform(post("/plants/addDayTag")
                .param("tagContent",tag)
                .param("plantDayID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plantDay"))
                .andExpect(content().string(containsString(tag)))
                .andExpect(view().name("plants/plantFragments :: dayTagFragment"));


    }

    @Test @Transactional
    public void testAddAttribToPlantDay() throws Exception{

        PlantDay day = plantDayManager.getPlantDayByID(2);
        String id = Long.toString(day.getId());
        String aName = "testAttributeName";
        String aValue = "testAttributeValue";


        this.mockMvc.perform(post("/plants/addDayAttribute")
                .param("attribName",aName)
                .param("attribVal",aValue)
                .param("plantDayID",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("plantDay"))
                .andExpect(content().string(containsString(aValue)))
                .andExpect(content().string(containsString(aName)))
                .andExpect(view().name("plants/plantFragments :: dayAttribFragment"));
    }



}
