package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.dto.UserDto;
import br.com.ero.instagram_web_api.dto.UserUpdateDto;
import br.com.ero.instagram_web_api.dto.mapper.UserMapper;
import br.com.ero.instagram_web_api.exceptions.*;
import br.com.ero.instagram_web_api.jwt.JwtTokenClaims;
import br.com.ero.instagram_web_api.jwt.JwtTokenProvider;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.UserRepository;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public User registerNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailUniqueViolationException(String.format("Email: {%s} already exists.", user.getEmail()));
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameUniqueViolationException(String.format("Username: {%s} already exists.", user.getEmail()));
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        return userRepository.save(newUser);
    }

    @Override
    public User findByEmail(Authentication authentication) {
        Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new BadCredentialsException("Invalid Username or Invalid Password");
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException("No user with this id was found: " + id);
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException("No user with this username was found: " + username);
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) {
        return userRepository.findAllUsersByUserIds(userIds);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.findByQuery(query);
        if (users.isEmpty()) {
            throw new UserNotFoundException("User not Found");
        }
        return users;
    }

    @Override
    public User findUserProfile(String token) {
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token.substring(7));
        Optional<User> optionalUser = userRepository.findByEmail(jwtTokenClaims.getUsername());

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new InvalidTokenException("Invalid Token");
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        boolean alreadyFollowing = reqUser.getFollowing().stream().anyMatch(user -> user.getId().equals(followUserId));
        if (alreadyFollowing) {
            throw new AlreadyFollowingUseException("User [" + reqUser.getUsername() + "] already follows user [" + followUser.getUsername() + "]");
        }

        UserDto follower = UserMapper.toUserDto(reqUser);
        UserDto following = UserMapper.toUserDto(followUser);

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You are following " + followUser.getUsername();
    }

    @Override
    public String unfollowUser(Integer reqUserId, Integer followUserId) {
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = UserMapper.toUserDto(reqUser);
        UserDto following = UserMapper.toUserDto(followUser);

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You have unfollowed " + followUser.getUsername();
    }

    @Override
    public void updateUserDetails(UserUpdateDto updateUser, User existingUser) {
        try {
            if (updateUser.getBio() != null) existingUser.setBio(updateUser.getBio());
            if (updateUser.getName() != null) existingUser.setName(updateUser.getName());
            if (updateUser.getUsername() != null) existingUser.setUsername(updateUser.getUsername());
            if (updateUser.getMobile() != null) existingUser.setMobile(updateUser.getMobile());
            if (updateUser.getGender() != null) existingUser.setGender(updateUser.getGender());
            if (updateUser.getWebsite() != null) existingUser.setWebsite(updateUser.getWebsite());
            if (updateUser.getImage() != null) existingUser.setImage(updateUser.getImage());

            userRepository.save(existingUser);

        } catch (UserNotAllowedException exception) {
            throw new UserNotAllowedException("You can update this User");
        }
    }

    @Override
    public void updatePassword(Integer id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("New password and password confirmation don't match");
        }

        User user = findUserById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
