package pl.dors.radek.model;

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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}