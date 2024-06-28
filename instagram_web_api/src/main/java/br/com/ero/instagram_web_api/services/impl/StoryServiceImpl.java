package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.exceptions.StoryNotFoundException;
import br.com.ero.instagram_web_api.modal.Story;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.StoryRepository;
import br.com.ero.instagram_web_api.services.StoryService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;

    @Override
    public Story createStory(Story story, Integer userId) {
        User user = userService.findUserById(userId);
        UserDto userDto = UserMapper.toUserDto(user);

        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) {
        User user = userService.findUserById(userId);
        List<Story> stories = user.getStories();

        if (stories.isEmpty()) {
            throw new StoryNotFoundException("This user doesn't have any story.");
        }
        return stories;
    }
}
