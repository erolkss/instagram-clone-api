package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.modal.Comment;

public interface CommentService {
    Comment createComment(Comment comment, Integer postId, Integer userId);

    Comment likeComment(Integer commentId, Integer userId);

    Comment findCommentById(Integer commentId);
}
