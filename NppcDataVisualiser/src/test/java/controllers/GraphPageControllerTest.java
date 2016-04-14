package controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class GraphPageControllerTest extends AbstractControllerTest{


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private HashMap<String, Object> sessionattr;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        sessionattr = new HashMap<>();
        sessionattr.put("experimentCode","testCode");
    }

    @Test
    public void testShowGraphsPageNoExperiment() throws Exception {
        this.mockMvc.perform(get("/graphs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>No Data</title>")))
                .andExpect(view().name("graphs/noData"));
    }

    @Test
    public void testShowGraphsPageWithExperiment() throws Exception {
        this.mockMvc.perform(get("/graphs").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("graphCreationInfo"))
                .andExpect(model().attributeExists("availableAttribs"))
                .andExpect(content().string(containsString("<title>Graphs Page</title>")))
                .andExpect(view().name("graphs/show"));
    }

    @Test
    public void testShowPlantDetailGraphs() throws Exception {

        String plantInSystemBarcode = "bc1";

        this.mockMvc.perform(get("/graphs/byPlant/"+plantInSystemBarcode).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("attribs"))
                .andExpect(model().attributeExists("plant"))
                .andExpect(content().string(containsString("<title>Plant Graphs Page</title>")))
                .andExpect(view().name("graphs/forPlant"));
    }

    @Test
    public void testShowPlantDetailGraphsNoPlant() throws Exception {

        String plantNotInSystemBarcode = "bc not in system";

        this.mockMvc.perform(get("/graphs/byPlant/"+plantNotInSystemBarcode).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("barcode"))
                .andExpect(content().string(
                        containsString("Could not find plant with barcode " + plantNotInSystemBarcode)))
                .andExpect(view().name("plants/notfound"));
    }

    @Test
    public void testGetGraphData() throws Exception {

        String attrib1 = "test";
        String attrib2 = "test2";

        this.mockMvc.perform(post("/graphs/create")
                .param("xAxisAttribute",attrib1)
                .param("yAxisAttribute",attrib2)
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                verify JSON contains attributes defined in data.sql
                .andExpect(content().string(
                        containsString("{\"y\":[\"a\",\"b\",\"c\"],\"x\":[\"1\",\"2\",\"3\"]}")));

    }

    @Test
    public void testGetGraphDataForPlant() throws Exception {

        String plantInSystemBarcode = "bc1";
        String attrib1 = "attr1";
        String attrib2 = "attr2";

        this.mockMvc.perform(get("/graphs/byPlant/"+plantInSystemBarcode+"/fromData/"+attrib1+"/"+attrib2)
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                verify JSON contains attributes defined in data.sql
                .andExpect(content().string(
                        containsString("{\"y\":[\"val2\",\"val1\"],\"x\":")));

    }

    @Test
    public void testPlantsFromGraphQuery() throws Exception {


        String keyX = "test";
        String valX = "1";
        String keyY = "test2";
        String valY = "a";

        this.mockMvc.perform(get("/graphs/graphClickQuery")
                .param("keyX",keyX)
                .param("valX",valX)
                .param("keyY",keyY)
                .param("valY",valY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("results"))
                .andExpect(content().string(
                        containsString(" <p>Number of results : 1</p>")));

    }



}
