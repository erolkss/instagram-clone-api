package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.responsesdto.PostCreateResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.PostMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.MessageResponse;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "Contains all resource-related operations to do the post ")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Operation(
            summary = "Create a new Post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Post successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request fields is mandatory", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))

            }
    )
    @PostMapping("/create")
    public ResponseEntity<PostCreateResponseDto> createPostHandler(@RequestBody PostCreateDto post, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createNewPost(post, user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toResponseCreateDto(createdPost));
    }

    @Operation(
            summary = "Find posts by User Id ", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found post Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "This user does not have any post.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @GetMapping("/all/{id}")
    public ResponseEntity<List<PostCreateResponseDto>> findPostByUserIdsHandler(@PathVariable("id") Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toListResponseDto(posts));
    }


    @Operation(
            summary = "Find the posts of the users you are following", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts found Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No post available", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @GetMapping("/following/{ids}")
    public ResponseEntity<List<PostCreateResponseDto>> findAllPostByUserIdsHandler(@PathVariable("ids") List<Integer> userIds) {
        List<Post> posts = postService.findAllPostByUserIds(userIds);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toListResponseDto(posts));
    }

    @Operation(
            summary = "Find post by id", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts found Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @GetMapping("/{postId}")
    public ResponseEntity<PostCreateResponseDto> findPostByIdHandler(@PathVariable("postId") Integer postId) {
        Post post = postService.findPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(post));
    }


    @Operation(
            summary = "Like a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The like was successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/like/{postId}")
    public ResponseEntity<PostCreateResponseDto> likePostHandler(@PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post likedPost = postService.likePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(likedPost));

    }

    @Operation(
            summary = "Unlike a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The unlike was successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/unlike/{postId}")
    public ResponseEntity<PostCreateResponseDto> unlikePostHandler(@PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Post unlikedPost = postService.unlikePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(PostMapper.toResponseCreateDto(unlikedPost));

    }

    @Operation(
            summary = "Delete a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Delete Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePostHandler(@PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        postService.deletePost(postId, user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Save the post in 'saved'", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post saved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @Operation(
            summary = "Unsaved the post in 'saved'", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post unsaved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String message = postService.unSavedPost(postId, user.getId());

        MessageResponse messageResponse = new MessageResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }
}
