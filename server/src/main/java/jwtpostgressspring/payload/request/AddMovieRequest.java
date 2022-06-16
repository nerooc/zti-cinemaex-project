package jwtpostgressspring.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddMovieRequest {
    private Long director;

    @NotBlank
    @Size(max = 20)
    private String title;

    @NotBlank
    private String release;

    @NotBlank
    private String duration;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 200)
    private String image;

    public Long getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String setDescription(String description) {
        return this.description = description;
    }

    public String setImage(String image) {
        return this.image = image;
    }

    public String setTitle(String title) {
        return this.title = title;
    }

    public String setDuration(String duration) {
        return this.duration = duration;
    }

    public String setRelease(String release) { return this.release = release; }

    public Long setDirector(Long director) {
       return this.director = director;
    }
}
