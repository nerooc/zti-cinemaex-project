package jwtpostgressspring.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddActorRequest {
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String surname;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 200)
    private String image;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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

    public String setName(String name) {
        return this.name = name;
    }

    public String setSurname(String surname) {
        return this.surname = surname;
    }

}
