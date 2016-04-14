package controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created on 24/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * Basic test, based on examples given in https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples
 */
public class HomePageControllerWebTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private String experimentCode = "testCode";


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testShowHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Home")))
                .andExpect(content().string(containsString(experimentCode)));
    }

    @Test
    public void testRedirectToPlantsPage() throws Exception {
        this.mockMvc.perform(get("/setSessionExperiment/"+experimentCode))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plants"));
    }





}
