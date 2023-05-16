package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfoDetail;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatBotService {


    List<HaruChatMessage> getFirstMsgFromChatGPT(SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException;

    @Async
    List<HaruChatMessage> receiveMsgIfNoRequest(SelectedHaruInfoDetail selectedHaruInfoDetail) throws ExecutionException, InterruptedException;

    List<HaruChatMessage> getMsgFromChatGPT(SelectedHaruInfoDetail selectedHaruInfoDetail);
}
