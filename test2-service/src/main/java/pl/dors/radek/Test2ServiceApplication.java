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
public class Test2ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Test2ServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/test2")
class Test2Controller {

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> findAll() {
        Book b1 = new Book(1L, "Book1");
        Book b2 = new Book(2L, "Book2");
        Book b3 = new Book(3L, "Book3");
        Book b4 = new Book(4L, "Book4");
        Book b5 = new Book(5L, "Book5");
        return Stream.of(b1, b2, b3, b4, b5).collect(Collectors.toList());
    }
}

class Book {

    private Long id;
    private String title;

    Book() {
    }

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
