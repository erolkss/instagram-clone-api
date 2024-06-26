package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateDto {

    @NotBlank(message = "Caption is mandatory")
    private String caption;
    @NotBlank(message = "Image is mandatory")
    private String image;
    private String location;
}
