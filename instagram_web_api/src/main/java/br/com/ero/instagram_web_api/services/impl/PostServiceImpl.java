package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.dto.mapper.PostMapper;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.PostRepository;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Post createNewPost(PostCreateDto postCreateDto, Integer userId) {
        User user = userService.findUserById(userId);

        UserDto userDto = UserMapper.toUserDto(user);

        Post post = PostMapper.toPost(postCreateDto);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(userDto);

        return postRepository.save(post);
    }
}
