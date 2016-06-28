package pl.dors.radek;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
public class Test3ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test3ServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@RestController
@RequestMapping("/api")
class Test3Controller {

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

    @HystrixCommand(fallbackMethod = "fallbackRestRemplate")
    @RequestMapping(path = "/2", method = RequestMethod.GET)
    public List<String> restTempl() {
        ResponseEntity<List<Person>> exchange =
                this.restTemplate.exchange(
                        "http://Test1/test1",
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

    public List<String> fallbackRestRemplate() {
        return Stream.of("fallbackRestRemplate").collect(Collectors.toList());
    }

}