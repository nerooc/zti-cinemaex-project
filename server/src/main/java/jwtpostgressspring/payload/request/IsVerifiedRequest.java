package jwtpostgressspring.payload.request;

import javax.validation.constraints.NotBlank;

public class IsVerifiedRequest {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
