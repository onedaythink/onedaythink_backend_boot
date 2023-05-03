package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;

public interface ChatGPTService {

    HaruChatMessage getChatGPTResponse(HaruChatMessage haruChatMessage);

    String getTranslatedPrompt(HaruChatMessage haruChatRoomMessage) throws JsonProcessingException;
}
