package br.com.ero.instagram_web_api.controllers;


import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUserHandler(@RequestParam("q") String query) {
        List<User> users = userService.searchUser(query);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponseDto(users));
    }
}
