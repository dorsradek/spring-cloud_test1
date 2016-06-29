package pl.dors.radek.model;

/**
 * Created by rdors on 2016-06-29.
 */
public class Person {

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
