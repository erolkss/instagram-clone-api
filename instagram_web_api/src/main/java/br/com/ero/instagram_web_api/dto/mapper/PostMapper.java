package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.PostCreateResponseDto;
import br.com.ero.instagram_web_api.modal.Post;
import org.modelmapper.ModelMapper;

public class PostMapper {
    public static Post toPost(PostCreateDto postCreateDto) {
        return new ModelMapper().map(postCreateDto, Post.class);
    }

    public static PostCreateResponseDto toResponseCreateDto(Post post) {
        return new ModelMapper().map(post, PostCreateResponseDto.class);
    }
}
