package pl.dors.radek.controller;

import com.netflix.config.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import pl.dors.radek.Test3ServiceApplication;
import pl.dors.radek.TestCommons;
import pl.dors.radek.feign.PersonClientFeign;
import pl.dors.radek.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rdors on 2016-06-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Test3ServiceApplication.class)
@WebAppConfiguration
@IntegrationTest({"eureka.client.enabled=false"})
public class Test3ControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private Test3Controller test3Controller;

    @Mock
    private PersonClientFeign personClientFeign;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DiscoveryClient discoveryClient;

    @Autowired
    private TestCommons testCommons;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(test3Controller).build();

        mockPersonClientFeign();
        mockRestTemplate();
        mockDiscoveryClient();
    }


    private void mockPersonClientFeign() {
        Person p1 = new Person(1L, "Stefan");
        Person p2 = new Person(2L, "Marian");
        List<Person> mockedResult = Stream.of(p1, p2).collect(Collectors.toList());

        Mockito.when(personClientFeign.getPersons()).thenReturn(mockedResult);
    }

    private void mockRestTemplate() {
        Person p1 = new Person(1L, "Stefan");
        Person p2 = new Person(2L, "Marian");
        List<Person> mockedResult = Stream.of(p1, p2).collect(Collectors.toList());

        ResponseEntity<List<Person>> responseEntity = new ResponseEntity<>(mockedResult, null, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Matchers.anyString(),
                Matchers.any(HttpMethod.class),
                Matchers.any(),
                Matchers.<ParameterizedTypeReference<List<Person>>>any()))
                .thenReturn(responseEntity);
    }

    private void mockDiscoveryClient() {
        Mockito.when(discoveryClient.getServices())
                .thenReturn(Arrays.asList("Test8", "Test4"));
    }

    @Test
    public void feignTest() throws Exception {
        List<String> expectedResult = Stream.of("Stefan", "Marian").collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }

    @Test
    public void restTemplateTest() throws Exception {
        List<String> expectedResult = Stream.of("Stefan", "Marian").collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }

    @Test
    public void discClientTest() throws Exception {
        List<String> expectedResult = Stream.of("Test8", "Test4").collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }
}
