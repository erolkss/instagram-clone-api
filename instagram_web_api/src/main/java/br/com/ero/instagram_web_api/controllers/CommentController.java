package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.CommentCreateDto;
import br.com.ero.instagram_web_api.dto.CommentResponseDto;
import br.com.ero.instagram_web_api.dto.PostCreateResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.CommentMapper;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import br.com.ero.instagram_web_api.modal.Comment;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.CommentRepository;
import br.com.ero.instagram_web_api.services.CommentService;
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

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Contains all resource-related operations to comments the post")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;


    @Operation(
            summary = "Create a a comment in a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added comment to the post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))

            }
    )
    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentResponseDto> createCommentHandler(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Integer postId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Comment createdComment = commentService.createComment(CommentMapper.toComment(commentCreateDto), postId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(createdComment));
    }

    @Operation(
            summary = "Like Comment a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The like was successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/like/{commentId}")
    public ResponseEntity<CommentResponseDto> likeCommentHandler(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        User user = userService.findUserProfile(token);
        Comment comment = commentService.likeComment(commentId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(comment));
    }

    @Operation(
            summary = "Unlike Comment a post", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The unlike was successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostCreateResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<CommentResponseDto> unlikeCommentHandler(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        User user = userService.findUserProfile(token);
        Comment comment = commentService.unlikeComment(commentId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(comment));

    }


}
