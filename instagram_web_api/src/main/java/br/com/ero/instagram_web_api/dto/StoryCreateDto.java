package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoryCreateDto {

    @NotBlank(message = "Image is mandatory")
    private String image;
    private String caption;
}
