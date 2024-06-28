package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.StoryCreateDto;
import br.com.ero.instagram_web_api.dto.StoryResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.StoryMapper;
import br.com.ero.instagram_web_api.modal.Story;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.StoryService;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {

    private final UserService userService;
    private final StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<StoryResponseDto> createStoryHandler(@RequestBody StoryCreateDto story, @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(StoryMapper.toStory(story), user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(StoryMapper.toStoryResponseDto(createdStory));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponseDto>> findAllStoryByUserIdHandler(@PathVariable Integer userId) {
        userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(StoryMapper.toListResponseDto(stories));
    }
}
