package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.UserRegisterDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.UserRegisterResponseDto;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.exceptions.dto.ErrorMessageException;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Contains all resource-related operations to register a new user and login")
public class AuthController {

    private final UserService userService;


    @Operation(
            summary = "Register a New User", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request fields is mandatory", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<UserRegisterResponseDto> registerNewUserHandler(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        User user = userService.registerNewUser(UserMapper.toUser(userRegisterDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toRegisterResponseDto(user));
    }


    @Operation(
            summary = "Authentication API", description = "Resource to authentication with API",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Authentication successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageException.class)))
            }
    )
    @GetMapping("/signin")
    public ResponseEntity<UserResponseDto> signHandler(Authentication authentication) {
        User user = userService.findByEmail(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(UserMapper.toResponseDto(user));
    }
}
