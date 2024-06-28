package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.CommentCreateDto;
import br.com.ero.instagram_web_api.dto.CommentResponseDto;
import br.com.ero.instagram_web_api.modal.Comment;
import org.modelmapper.ModelMapper;

public class CommentMapper {

    public static Comment toComment(CommentCreateDto commentCreateDto) {
        return new ModelMapper().map(commentCreateDto, Comment.class);
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        return new ModelMapper().map(comment, CommentResponseDto.class);
    }

}
