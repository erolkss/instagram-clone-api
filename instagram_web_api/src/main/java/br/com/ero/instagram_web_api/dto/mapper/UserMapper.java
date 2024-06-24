package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.UserRegisterDto;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.User;
import org.modelmapper.ModelMapper;
public class UserMapper {

    public static User toUser(UserRegisterDto userRegisterDto) {
        return new ModelMapper().map(userRegisterDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        return new ModelMapper().map(user, UserResponseDto.class);
    }


}
