package br.com.ero.instagram_web_api.dto.responsesdto;

import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.modal.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PostCreateResponseDto {

    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    private UserDto user;
    private List<Comment> comments = new ArrayList<>();
    private Set<UserDto> likeByUsers = new HashSet<>();
}
