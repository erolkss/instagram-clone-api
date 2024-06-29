package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDto {

    @NotBlank(message = "Content is mandatory")
    private String content;
}
