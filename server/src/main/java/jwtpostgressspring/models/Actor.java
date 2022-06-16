package jwtpostgressspring.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_actor;

    @NotBlank
    @Size(max = 20)
    private String actor_name;

    @NotBlank
    @Size(max = 20)
    private String actor_surname;

    @NotBlank
    @Size(max = 1000)
    private String actor_description;

    @NotBlank
    @Size(max = 200)
    private String actor_image;

    public Actor(){}

    public Actor(String actor_name, String actor_surname, String actor_description, String actor_image) {
        this.actor_name = actor_name;
        this.actor_surname = actor_surname;
        this.actor_description = actor_description;
        this.actor_image = actor_image;
    }

    private java.sql.Timestamp createAt;

    public Long getId() {
        return id_actor;
    }

    public void setId(Long id) {
        this.id_actor = id;
    }

    public String getName() {
        return actor_name;
    }

    public String getSurname() {
        return actor_surname;
    }

    public String getDescription() {
        return actor_description;
    }

    public String getImage() {
        return actor_image;
    }
}
