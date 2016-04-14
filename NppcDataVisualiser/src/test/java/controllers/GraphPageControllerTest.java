package controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(content().string(containsString("<title>No Data</title>")));
    }

    @Test
    public void testShowGraphsPageWithExperiment() throws Exception {
        this.mockMvc.perform(get("/graphs").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Graphs Page</title>")));
    }


}
