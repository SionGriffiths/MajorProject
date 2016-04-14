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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created on 14/04/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class TagPageControllerTest extends AbstractControllerTest {

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
    public void testShowTags() throws Exception {
        this.mockMvc.perform(get("/tags").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Tag Page")))
                .andExpect((content().string(containsString("content 1"))))
                .andExpect((content().string(containsString("content 2"))))
                .andExpect((content().string(containsString("content 3"))))
                .andExpect((content().string(containsString("content 4"))))
                .andExpect(view().name("tags/show"));


    }

    @Test
    public void testShowTagsNoExperiment() throws Exception {
        this.mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Tag Page")))
                .andExpect((content().string(containsString("There are 0 tags in the system for the current experiment"))))
                .andExpect(view().name("tags/show"));


    }

    @Test
    public void testDisplayResults() throws Exception {

        String availablecontent = "content 1";

        this.mockMvc.perform(get("/tags/"+availablecontent).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("plants with tag "+availablecontent)))
                .andExpect(content().string(containsString("days/images with tag "+availablecontent)))
                .andExpect(view().name("tags/result"));

    }

    @Test
    public void testDisplayUnavilableResults() throws Exception {

        String unavailableContent = "Not in the System";

        this.mockMvc.perform(get("/tags/"+unavailableContent).sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Could not find tag with content "+unavailableContent)))
                .andExpect(view().name("tags/notfound"));

    }


}
