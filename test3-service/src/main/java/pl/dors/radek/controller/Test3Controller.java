package pl.dors.radek.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.dors.radek.feign.PersonClientFeign;
import pl.dors.radek.model.Person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rdors on 2016-06-30.
 */
@RestController
@RequestMapping("/api")
public class Test3Controller {

    private PersonClientFeign personClientFeign;
    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;

    @Autowired
    public Test3Controller(PersonClientFeign personClientFeign, DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.personClientFeign = personClientFeign;
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallbackFeign")
    @RequestMapping(path = "/1", method = RequestMethod.GET)
    public List<String> feign() {
        return personClientFeign.getPersons().stream().map(person -> person.getName()).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "fallbackRestTemplate")
    @RequestMapping(path = "/2", method = RequestMethod.GET)
    public List<String> restTemplate() {
        ResponseEntity<List<Person>> exchange =
                this.restTemplate.exchange(
                        "http://test1-service/test1",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        });
        return exchange.getBody().stream().map(person -> person.getName()).collect(Collectors.toList());
    }

    @RequestMapping(path = "/3", method = RequestMethod.GET)
    public List<String> discClient() {
        return discoveryClient.getServices();
    }

    public List<String> fallbackFeign() {
        return Stream.of("fallbackFeign").collect(Collectors.toList());
    }

    public List<String> fallbackRestTemplate() {
        return Stream.of("fallbackRestTemplate").collect(Collectors.toList());
    }

}
