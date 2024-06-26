package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.modal.Post;

import java.util.List;

public interface PostService {
    Post createNewPost(PostCreateDto post, Integer userId);

    List<Post> findPostByUserId(Integer userId);

    List<Post> findAllPostByUserIds(List<Integer> userIds);

    Post findPostById(Integer postId);

    Post likePost(Integer postId, Integer userId);

    Post unlikePost(Integer postId, Integer userId);

    void deletePost(Integer postId, Integer userId);

    String savedPost(Integer postId, Integer userId);

    String unSavedPost(Integer postId, Integer userId);
}