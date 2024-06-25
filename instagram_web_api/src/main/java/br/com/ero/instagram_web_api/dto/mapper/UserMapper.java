package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.UserRegisterDto;
import br.com.ero.instagram_web_api.dto.responsesdto.UserRegisterResponseDto;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserRegisterDto userRegisterDto) {
        return new ModelMapper().map(userRegisterDto, User.class);
    }

    public static UserRegisterResponseDto toRegisterResponseDto(User user) {
        return new ModelMapper().map(user, UserRegisterResponseDto.class);
    }

    public static UserResponseDto toResponseDto(User user) {
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListResponseDto(List<User> users) {
        return users.stream().map(UserMapper::toResponseDto).collect(Collectors.toList());
    }


}
