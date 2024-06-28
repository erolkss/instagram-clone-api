package br.com.ero.instagram_web_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryResponseDto {

    private Integer id;
    private String image;
    private String caption;
    private LocalDateTime timestamp;
    private UserDto user;
}
