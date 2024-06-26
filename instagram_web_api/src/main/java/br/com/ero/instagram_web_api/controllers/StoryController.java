package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.StoryCreateDto;
import br.com.ero.instagram_web_api.dto.responsesdto.StoryResponseDto;
import br.com.ero.instagram_web_api.dto.mapper.StoryMapper;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import br.com.ero.instagram_web_api.modal.Story;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.StoryService;
import br.com.ero.instagram_web_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Tag(name = "Story", description = "Contains all resource-related operations to stories")

public class StoryController {

    private final UserService userService;
    private final StoryService storyService;


    @Operation(
            summary = "Create a Story", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Story created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))

            }
    )
    @PostMapping("/create")
    public ResponseEntity<StoryResponseDto> createStoryHandler(@Valid @RequestBody StoryCreateDto story, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(StoryMapper.toStory(story), user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(StoryMapper.toStoryResponseDto(createdStory));
    }

    @Operation(
            summary = "Return all stories by user id", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stories retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "This user doesn't have any story", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),

            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponseDto>> findAllStoryByUserIdHandler(@PathVariable Integer userId) {
        userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(StoryMapper.toListResponseDto(stories));
    }
}
