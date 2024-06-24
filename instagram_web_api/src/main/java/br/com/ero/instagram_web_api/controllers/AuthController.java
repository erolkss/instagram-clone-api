package br.com.ero.instagram_web_api.controllers;

import br.com.ero.instagram_web_api.dto.UserRegisterDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.UserService;
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
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> registerNewUserHandler(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        User user = userService.registerNewUser(UserMapper.toUser(userRegisterDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signHandler(Authentication authentication) {
        User user = userService.findByEmail(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
}
