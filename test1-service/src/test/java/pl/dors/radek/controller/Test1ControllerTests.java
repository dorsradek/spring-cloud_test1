package pl.dors.radek.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import pl.dors.radek.Test1ServiceApplication;
import pl.dors.radek.TestCommons;
import pl.dors.radek.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test1ServiceApplication.class)
@WebAppConfiguration
@IntegrationTest({"eureka.client.enabled=false"})
public class Test1ControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestCommons testCommons;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAllTest() throws Exception {
        Person p1 = new Person(1L, "Adam");
        Person p2 = new Person(2L, "Bartek");
        Person p3 = new Person(3L, "Czarek");
        Person p4 = new Person(4L, "Dawid");
        Person p5 = new Person(5L, "Edward");
        List<Person> expectedResult = Stream.of(p1, p2, p3, p4, p5).collect(Collectors.toList());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/test1/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }

}
