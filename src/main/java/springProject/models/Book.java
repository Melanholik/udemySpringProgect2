package springProject.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "The 'name' field cannot be empty")
    @Size(min = 2, message = "The 'name' field must contain at least 2 characters")
    private String name;

    @Column(name = "author")
    @NotBlank(message = "The 'author' field cannot be empty")
    @Size(min = 2, message = "The 'author' field must contain at least 2 characters")
    private String author;

    @Column(name = "release_year")
    @NotNull(message = "The 'releaseYear' field cannot be empty")
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "take_time")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date takeTame;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public Book(int id, String name, String author, int releaseYear) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getTakeTame() {
        return takeTame;
    }

    public void setTakeTame(Date takeTame) {
        this.takeTame = takeTame;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
