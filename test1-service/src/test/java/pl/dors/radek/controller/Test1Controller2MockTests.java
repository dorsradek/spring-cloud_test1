package pl.dors.radek.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.dors.radek.Test1ServiceApplication;
import pl.dors.radek.TestCommons;
import pl.dors.radek.model.Person;
import pl.dors.radek.model.Person2;
import pl.dors.radek.repository.Person2Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test1ServiceApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class Test1Controller2MockTests {

    private MockMvc mockMvc;

    @InjectMocks
    private Test1Controller2 test1Controller2;

    @Mock
    private Person2Repository person2Repository;

    @Autowired
    private TestCommons testCommons;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(test1Controller2).build();

        Person2 p1 = new Person2(1L, "Stefan");
        List<Person2> expectedResult = Stream.of(p1).collect(Collectors.toList());
        Mockito.when(person2Repository.findAll()).thenReturn(expectedResult);
    }

    @Test
    public void findAllTest() throws Exception {
        Person p1 = new Person(1L, "Stefan");
        List<Person> expectedResult = Stream.of(p1).collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/test2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }


}
