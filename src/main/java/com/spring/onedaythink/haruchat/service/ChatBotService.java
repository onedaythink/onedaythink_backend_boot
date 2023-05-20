package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfoDetail;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatBotService {

    List<HaruChat> getRandomHaruBot();

    // select All chat rooms by userNo
    List<HaruChatRoomDetail> getChatRoomsByUserNo(HaruChatRoomDetail haruChatRoomDetail);

    // close chat room
    int closeChatRoom(HaruChatRoom haruChatRoom);

    List<HaruChatMessage> getFirstMsgFromChatGPT(SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException;


    List<HaruChatMessage> receiveMsgIfNoRequest(SelectedHaruInfoDetail selectedHaruInfoDetail) throws ExecutionException, InterruptedException;

    List<HaruChatMessage> getMsgFromChatGPT(SelectedHaruInfoDetail selectedHaruInfoDetail);

}
