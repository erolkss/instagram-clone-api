package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.dto.PostCreateDto;
import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.dto.mapper.PostMapper;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.exceptions.PostNotFoundException;
import br.com.ero.instagram_web_api.exceptions.UserNotFoundException;
import br.com.ero.instagram_web_api.modal.Post;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.PostRepository;
import br.com.ero.instagram_web_api.repositories.UserRepository;
import br.com.ero.instagram_web_api.services.PostService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public Post createNewPost(PostCreateDto postCreateDto, Integer userId) {
        User user = userService.findUserById(userId);

        UserDto userDto = UserMapper.toUserDto(user);

        Post post = PostMapper.toPost(postCreateDto);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(userDto);

        return postRepository.save(post);
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            throw new UserNotFoundException("This user does not have any post.");
        }
        return posts;
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if (posts.isEmpty()) {
            throw new PostNotFoundException("No post available");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            return optionalPost.get();
        }
        throw new PostNotFoundException("Post not found with id: " + postId);
    }

    @Override
    public Post likePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = UserMapper.toUserDto(user);

        post.getLikeByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unlikePost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = UserMapper.toUserDto(user);

        post.getLikeByUsers().remove(userDto);

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Integer postId, Integer userId) {
        try {
            Post post = findPostById(postId);
            User user = userService.findUserById(userId);

            if (post.getUser().getId().equals(user.getId())) {
                postRepository.deleteById(post.getId());
            }
        } catch (PostNotFoundException ex) {
            throw new PostNotFoundException("You can't delete other user's post!");
        }

    }

    @Override
    public String savedPost(Integer postId, Integer userId) {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post Save Successfully";
    }
}
