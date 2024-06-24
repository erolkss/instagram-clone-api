package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Full Name is mandatory")
    private String name;
    @NotBlank(message = "E-mail is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;

}

