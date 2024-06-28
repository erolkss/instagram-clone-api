package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.exceptions.CommentNotFoundException;
import br.com.ero.instagram_web_api.modal.Comment;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.CommentRepository;
import br.com.ero.instagram_web_api.repositories.PostRepository;
import br.com.ero.instagram_web_api.services.CommentService;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = UserMapper.toUserDto(user);

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);

        postRepository.save(post);

        return createdComment;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = UserMapper.toUserDto(user);

        comment.getLikedByUsers().add(userDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Integer commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        }
        throw new CommentNotFoundException("Comment is Not exist with id: " + commentId);
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = UserMapper.toUserDto(user);

        comment.getLikedByUsers().remove(userDto);

        return commentRepository.save(comment);
    }
}
