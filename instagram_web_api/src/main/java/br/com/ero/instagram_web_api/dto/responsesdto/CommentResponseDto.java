package br.com.ero.instagram_web_api.dto.responsesdto;

import br.com.ero.instagram_web_api.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CommentResponseDto {

    private Integer id;
    private UserDto user;
    private String content;
    private Set<UserDto> likedByUsers = new HashSet<>();
    private LocalDateTime createdAt;
}
