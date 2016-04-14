package controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created on 01/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public class AdminPageControllerTest extends AbstractControllerTest {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
    }

    @Test
    public void testRedirectToLoginIfNotAuthorised() throws Exception {
        this.mockMvc.perform(get("/admin")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testShowAdminAuthorised() throws Exception {
        this.mockMvc.perform(get("/admin")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin Page</h1>")));
    }

    @Test
    public void testShowAdminLogin() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Administrator Sign-in")));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testAdminLogout() throws Exception {
        this.mockMvc.perform(get("/admin")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin Page</h1>")));



        this.mockMvc.perform(get("/admin/logout")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
