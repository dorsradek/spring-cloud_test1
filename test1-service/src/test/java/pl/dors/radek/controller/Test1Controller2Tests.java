package pl.dors.radek.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.Test1ServiceApplication;
import pl.dors.radek.TestCommons;
import pl.dors.radek.model.Person2;
import pl.dors.radek.repository.Person2Repository;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test1ServiceApplication.class)
@WebAppConfiguration
@IntegrationTest({"eureka.client.enabled=false"})
public class Test1Controller2Tests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Person2Repository person2Repository;

    @Autowired
    private TestCommons testCommons;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Person2 p1 = new Person2("Stefan");
        Person2 p2 = new Person2("Marian");
        person2Repository.deleteAllInBatch();
        Stream.of(p1, p2).forEach(person2Repository::save);
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Stefan")))
                .andExpect(jsonPath("$[1].name", is("Marian")));
    }
}
