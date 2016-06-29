package pl.dors.radek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;
import pl.dors.radek.model.Person2;
import pl.dors.radek.repository.Person2Repository;

import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
public class Test1ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test1ServiceApplication.class, args);
    }
}

@Component
class DummyCLR implements CommandLineRunner {

    private Person2Repository person2Repository;

    @Autowired
    public DummyCLR(Person2Repository person2Repository) {
        this.person2Repository = person2Repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Person2 p1 = new Person2("QWE");
        Person2 p2 = new Person2("ASD");
        Person2 p3 = new Person2("ZXC");
        Stream.of(p1, p2, p3).forEach(person -> person2Repository.save(person));
    }
}



