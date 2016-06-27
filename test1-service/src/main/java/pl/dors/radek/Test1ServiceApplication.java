package pl.dors.radek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
public class Test1ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test1ServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/test1")
class Test1Controller {

    @RequestMapping(method = RequestMethod.GET)
    public List<Person> findAll() {
        Person p1 = new Person(1L, "Adam");
        Person p2 = new Person(2L, "Bartek");
        Person p3 = new Person(3L, "Czarek");
        Person p4 = new Person(4L, "Dawid");
        Person p5 = new Person(5L, "Edward");
        return Stream.of(p1, p2, p3, p4, p5).collect(Collectors.toList());
    }
}

class Person {

    private Long id;
    private String name;

    Person() {
    }

    public Person(String name) {
        this(null, name);
    }

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}