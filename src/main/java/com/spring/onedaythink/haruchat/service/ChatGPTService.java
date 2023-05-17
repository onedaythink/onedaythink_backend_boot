package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface ChatGPTService {

//    String testAPIAutoCall();

    List<HaruChatMessage> someMethod(SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException;
    List<HaruChatMessage> getChatGPTResponse(SelectedHaruInfo selectedHaruInfo) throws JsonProcessingException;


}
