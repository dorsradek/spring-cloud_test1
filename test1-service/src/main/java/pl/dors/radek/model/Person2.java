package pl.dors.radek.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rdors on 2016-06-29.
 */
@Entity
public class Person2 {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    Person2() {
    }

    public Person2(String name) {
        this(null, name);
    }

    public Person2(Long id, String name) {
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
        return "Person2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
