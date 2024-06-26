package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.PostCreateResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.PostMapper;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<PostCreateResponseDto> createPostHandler(@RequestBody PostCreateDto post, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createNewPost(post, user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toResponseCreateDto(createdPost));
    }
}
