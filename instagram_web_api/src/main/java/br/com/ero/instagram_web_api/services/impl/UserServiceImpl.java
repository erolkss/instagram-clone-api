package br.com.ero.instagram_web_api.services.impl;

import br.com.ero.instagram_web_api.exceptions.UserEmailUniqueViolationException;
import br.com.ero.instagram_web_api.exceptions.UserNotFoundException;
import br.com.ero.instagram_web_api.exceptions.UsernameUniqueViolationException;
import br.com.ero.instagram_web_api.modal.User;
import br.com.ero.instagram_web_api.repositories.UserRepository;
import br.com.ero.instagram_web_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
        if (optionalUser.isPresent()) { return optionalUser.get(); }
        throw new UserNotFoundException("No user with this id was found: " + id);
    }
}
