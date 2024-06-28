package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.StoryCreateDto;
import br.com.ero.instagram_web_api.dto.StoryResponseDto;
import br.com.ero.instagram_web_api.dto.responsesdto.UserResponseDto;
import br.com.ero.instagram_web_api.modal.Story;
import br.com.ero.instagram_web_api.modal.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class StoryMapper {

    public static Story toStory(StoryCreateDto storyCreateDto) {
        return new ModelMapper().map(storyCreateDto, Story.class);
    }

    public static StoryResponseDto toStoryResponseDto(Story createdStory) {
        return new ModelMapper().map(createdStory, StoryResponseDto.class);
    }

    public static List<StoryResponseDto> toListResponseDto(List<Story> users) {
        return users.stream().map(StoryMapper::toStoryResponseDto).collect(Collectors.toList());
    }
}
