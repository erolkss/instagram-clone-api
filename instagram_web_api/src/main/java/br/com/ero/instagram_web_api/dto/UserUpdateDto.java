package br.com.ero.instagram_web_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String username;
    private String name;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;
}

