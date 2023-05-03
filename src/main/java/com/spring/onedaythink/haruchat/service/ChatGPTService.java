package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;

import java.util.List;

public interface ChatGPTService {

    List<HaruChatMessage> getChatGPTResponse(SelectedHaruInfo selectedHaruInfo) throws JsonProcessingException;

}
