package pl.dors.radek.model;

public class Book {

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
