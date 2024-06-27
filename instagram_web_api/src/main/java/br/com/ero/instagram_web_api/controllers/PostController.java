package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.PostCreateResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.PostMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.MessageResponse;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all/{id}")
    public ResponseEntity<List<PostCreateResponseDto>> findPostByUserIdsHandler(@PathVariable("id") Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toListResponseDto(posts));
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<PostCreateResponseDto>> findAllPostByUserIdsHandler(@PathVariable("ids") List<Integer> userIds) {
        List<Post> posts = postService.findAllPostByUserIds(userIds);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toListResponseDto(posts));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostCreateResponseDto> findPostByIdHandler(@PathVariable("postId") Integer postId) {
        Post post = postService.findPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(post));
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<PostCreateResponseDto> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(likedPost));

    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<PostCreateResponseDto> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post unlikedPost = postService.unlikePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(unlikedPost));

    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        postService.deletePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }
}
