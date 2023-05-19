package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatBotService {

    List<HaruChat> getRandomHaruBot();

    List<HaruChatMessage> getFirstMsgFromChatGPT(SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException;


    List<HaruChatMessage> receiveMsgIfNoRequest(SelectedHaruInfoDetail selectedHaruInfoDetail) throws ExecutionException, InterruptedException;

    List<HaruChatMessage> getMsgFromChatGPT(SelectedHaruInfoDetail selectedHaruInfoDetail);

}
