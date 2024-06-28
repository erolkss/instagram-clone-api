package br.com.ero.instagram_web_api.services;

import br.com.ero.instagram_web_api.modal.Story;

public interface StoryService {
    Story createStory(Story story, Integer userId);
}
