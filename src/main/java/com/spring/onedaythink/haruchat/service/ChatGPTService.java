package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.HaruChatMessage;

public interface ChatGPTService {

    HaruChatMessage getChatGPTResponse(HaruChatMessage haruChatMessage);

}
