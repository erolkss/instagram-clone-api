package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.modal.Post;

public interface PostService {
    Post createNewPost(PostCreateDto post, Integer userId);
}
