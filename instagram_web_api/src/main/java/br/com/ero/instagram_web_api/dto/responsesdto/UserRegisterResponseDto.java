package br.com.ero.instagram_web_api.dto.responsesdto;

import lombok.Data;

@Data
public class UserRegisterResponseDto {

    private Integer Id;
    private String username;
    private String name;
    private String email;
}
