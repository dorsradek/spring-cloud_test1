package pl.dors.radek.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.Test2ServiceApplication;
import pl.dors.radek.TestCommons;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test2ServiceApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class Test2ControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].title", is("Book1")))
                .andExpect(jsonPath("$[1].title", is("Book2")))
                .andExpect(jsonPath("$[2].title", is("Book3")))
                .andExpect(jsonPath("$[3].title", is("Book4")))
                .andExpect(jsonPath("$[4].title", is("Book5")));
    }
}