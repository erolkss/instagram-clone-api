package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Email(regexp = "^[a-z0-9.+-]+@[a-z--9.-]+\\.[a-z]{2,}$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 30)
    private String password;

}

