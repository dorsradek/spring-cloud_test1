package pl.dors.radek.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dors.radek.model.Person;

import java.util.List;

@FeignClient("Test1")
public interface PersonClientFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/test1")
    List<Person> getPersons();
}