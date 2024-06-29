package br.com.ero.instagram_web_api.controllers;


import br.com.ero.instagram_web_api.dto.UserUpdateDto;
import br.com.ero.instagram_web_api.dto.UserUpdatePasswordDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.MessageResponse;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Contains all resource-related operations for users")
@SecurityRequirement(name = "security")
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "Retrieve a user by Id", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No user with this id was found: {}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> findUserByIdHandler(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseDto(user));
    }

    @Operation(
            summary = "Retrieve a user by username", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No user with this username was found: {}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> findUseByUsernameHandler(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseDto(user));
    }


    @Operation(
            summary = "Retrieve a user by multiple ids", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resources retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No user with this id was found: {}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<UserResponseDto>> findUserByIdsHandler(@PathVariable List<Integer> userIds) {
        List<User> users = userService.findUserByIds(userIds);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponseDto(users));
    }


    @Operation(
            summary = "Search user by username or email address ", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Search results", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUserHandler(@RequestParam("q") String query) {
        List<User> users = userService.searchUser(query);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponseDto(users));
    }


    @Operation(
            summary = "Follow a user by userId", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Following user successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No user with this id was found: {}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String response = userService.followUser(user.getId(), followUserId);

        MessageResponse messageResponse = new MessageResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @Operation(
            summary = "Un follow a user by userId", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Unfollowing user successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "404", description = "No user with this id was found: {}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable Integer userId, @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        String response = userService.unfollowUser(user.getId(), userId);

        MessageResponse messageResponse = new MessageResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }


    @Operation(
            summary = "Find user profile by bearer token", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/req")
    public ResponseEntity<UserResponseDto> findUserProfileHandler(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        User user = userService.findUserProfile(token);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseDto(user));
    }

    @Operation(
            summary = "Update profile user", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "User profile successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @PutMapping("/account/edit")
    public ResponseEntity<Void> updateUserHandler(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token, @RequestBody UserUpdateDto updateDto) {
        User reqUser = userService.findUserProfile(token);
        userService.updateUserDetails(updateDto, reqUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "Update password profile user", description = "Request requires a bearer Token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "User profile successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "A field is incorrect", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @PatchMapping("/account/update_password")
    public ResponseEntity<Void> updateUserPasswordHandler(
            @Parameter(hidden = true) @RequestHeader("Authorization") String token,
            @Valid @RequestBody UserUpdatePasswordDto passwordDto
    ) {
        User user = userService.findUserProfile(token);
        userService.updatePassword(
                user.getId(),
                passwordDto.getCurrentPassword(),
                passwordDto.getNewPassword(),
                passwordDto.getConfirmPassword());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
