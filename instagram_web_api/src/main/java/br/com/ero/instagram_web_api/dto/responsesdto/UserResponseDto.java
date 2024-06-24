package br.com.ero.instagram_web_api.dto.responsesdto;

import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.Story;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserResponseDto {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;
    private Set<UserDto> follower = new HashSet<>();
    private Set<UserDto> following = new HashSet<>();
    private List<Story> stories = new ArrayList<>();
    private List<Post> savedPost = new ArrayList<>();
}