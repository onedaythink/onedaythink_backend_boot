package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.*;

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
