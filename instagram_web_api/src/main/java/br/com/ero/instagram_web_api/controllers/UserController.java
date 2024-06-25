package br.com.ero.instagram_web_api.controllers;


import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseDto(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> findUseByUsernameHandler(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponseDto(user));
    }

    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<UserResponseDto>> findUserByIdsHandler(@PathVariable List<Integer> userIds) {
        List<User> users = userService.findUserByIds(userIds);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponseDto(users));
    }
}