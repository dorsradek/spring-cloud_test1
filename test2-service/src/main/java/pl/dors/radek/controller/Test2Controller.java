package pl.dors.radek.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.model.Book;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/test2")
public class Test2Controller {

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