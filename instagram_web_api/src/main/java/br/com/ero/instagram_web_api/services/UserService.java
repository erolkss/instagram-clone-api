package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.modal.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    User registerNewUser(User user);

    User findByEmail (Authentication authentication);

}
