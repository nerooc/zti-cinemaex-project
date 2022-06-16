package jwtpostgressspring.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_director;

    @NotBlank
    @Size(max = 20)
    private String director_name;

    @NotBlank
    @Size(max = 20)
    private String director_surname;

    @NotBlank
    @Size(max = 1000)
    private String director_description;

    @NotBlank
    @Size(max = 200)
    private String director_image;

    public Director(){}

    public Director(String director_name, String director_surname, String director_description, String director_image) {
        this.director_name = director_name;
        this.director_surname = director_surname;
        this.director_description = director_description;
        this.director_image = director_image;
    }

    private java.sql.Timestamp createAt;

    public Long getId() {
        return id_director;
    }

    public void setId(Long id) {
        this.id_director = id;
    }

    public String getName() {
        return director_name;
    }

    public String getSurname() {
        return director_surname;
    }

    public String getDescription() {
        return director_description;
    }

    public String getImage() {
        return director_image;
    }
}
