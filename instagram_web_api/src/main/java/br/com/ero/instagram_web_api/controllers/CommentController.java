package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.CommentCreateDto;
import br.com.ero.instagram_web_api.dto.CommentResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.CommentMapper;
import br.com.ero.instagram_web_api.modal.Comment;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.CommentRepository;
import br.com.ero.instagram_web_api.services.CommentService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;


    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentResponseDto> createCommentHandler(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Integer postId, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Comment createdComment = commentService.createComment(CommentMapper.toComment(commentCreateDto), postId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(createdComment));
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<CommentResponseDto> likeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        User user = userService.findUserProfile(token);
        Comment comment = commentService.likeComment(commentId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(comment));
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<CommentResponseDto> unlikeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        User user = userService.findUserProfile(token);
        Comment comment = commentService.unlikeComment(commentId, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(CommentMapper.toCommentResponseDto(comment));

    }


}
