package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.dto.UserUpdateDto;
import br.com.ero.instagram_web_api.modal.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    User registerNewUser(User user);

    User findByEmail (Authentication authentication);

    User findUserById(Integer id);

    User findUserByUsername(String username);

    List<User> findUserByIds(List<Integer> userIds);

    List<User> searchUser(String query);

    User findUserProfile(String token);

    String followUser(Integer reqUserId, Integer followUserId);

    String unfollowUser(Integer reqUserId, Integer followUserId);

    void updateUserDetails(UserUpdateDto updateDto, User existingUser);

}
