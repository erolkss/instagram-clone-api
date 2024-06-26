package br.com.ero.instagram_web_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdatePasswordDto {

    @NotBlank
    @Size(min = 8, max = 30)
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 30)
    private String newPassword;
    @NotBlank
    @Size(min = 8, max = 30)
    private String confirmPassword;
}
