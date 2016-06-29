package pl.dors.radek.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.model.Person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/test1")
public class Test1Controller {

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