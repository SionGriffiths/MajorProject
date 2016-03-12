package controllers;

import com.siongriffiths.nppcdatavisualiser.Application;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantDao;
import defaults.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private PlantDao plantDao;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHome() throws Exception {
        this.mockMvc.perform(get("/plants")).andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Plants Page")));
    }


    @Test
    public void testPlant() throws Exception {
        String testBarCode = "55345";
        Plant plant = new Plant();
        plant.setBarCode(testBarCode);
        plantDao.save(plant);

        String plantUrl = "/plants/"+testBarCode;
        this.mockMvc.perform(get(plantUrl)).andExpect(status().isOk())
                .andExpect(content().string(containsString(testBarCode)));

        String notPlantUrl = "/plants/32435";
        this.mockMvc.perform(get(notPlantUrl)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Plant not Found")));

    }
}
