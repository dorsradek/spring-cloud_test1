package pl.dors.radek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.model.Person2;
import pl.dors.radek.repository.Person2Repository;

import java.util.List;

@RestController
@RequestMapping("/test2")
public class Test1Controller2 {

    private Person2Repository person2Repository;

    @Autowired
    public Test1Controller2(Person2Repository person2Repository) {
        this.person2Repository = person2Repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Person2> findAll() {
        return person2Repository.findAll();
    }
}