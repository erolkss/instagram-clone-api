package br.com.ero.instagram_web_api.dto.mapper;

import br.com.ero.instagram_web_api.dto.StoryCreateDto;
import br.com.ero.instagram_web_api.dto.StoryResponseDto;
import br.com.ero.instagram_web_api.modal.Story;
import org.modelmapper.ModelMapper;

public class StoryMapper {

    public static Story toStory(StoryCreateDto storyCreateDto) {
        return new ModelMapper().map(storyCreateDto, Story.class);
    }

    public static StoryResponseDto toStoryResponseDto(Story createdStory) {
        return new ModelMapper().map(createdStory, StoryResponseDto.class);
    }
}
