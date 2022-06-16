package jwtpostgressspring.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_movie;

    private Long id_director;

    @NotBlank
    @Size(max = 20)
    private String movie_title;

    @NotBlank
    @Size(max = 1000)
    private String movie_description;

    @NotBlank
    @Size(max = 200)
    private String movie_release;

    @NotBlank
    @Size(max = 200)
    private String movie_duration;

    @NotBlank
    @Size(max = 1000)
    private String movie_image;

    public Movie(){}

    public Movie(String movie_title, String movie_description, String movie_image, String movie_duration, String movie_release) {
        this.movie_title = movie_title;
        this.movie_description = movie_description;
        this.movie_image = movie_image;
        this.movie_duration = movie_duration;
        this.movie_release = movie_release;
    }

    private java.sql.Timestamp createAt;

    public Long getId() {
        return id_movie;
    }

    public void setId(Long id) {
        this.id_movie = id;
    }

    public String getTitle() {
        return movie_title;
    }

    public String getImage() {
        return movie_image;
    }

    public Long getDirector() {
        return id_director;
    }

    public String getDuration() {
        return movie_duration;
    }

    public String getRelease() { return movie_release; }

    public String getDescription() {
        return movie_description;
    }

}
