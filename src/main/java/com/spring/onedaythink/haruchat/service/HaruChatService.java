package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.user.vo.User;

import java.util.List;

public interface HaruChatService {

    // 하루봇 랜덤 조회
    List<HaruChat> getRandomHaruBot();
    /*
     채팅방 리스트 조회
     => 채팅방 대기/활성화/종료 조회(동적쿼리?)
     */

    // 채탕방 생성(사용자 하루봇 선택 반영)
    HaruChatRoom getHaruChatRoomInfo();
    int createHaruChatRoom(User user);
    int selectedHaruBot(HaruChat[] haruchats, HaruChatRoom haruChatRoom);

    // 채탕벙 수정(대기에서 수락/거절/종료)

    // 챠탕 메세지 전체 조회

    // 채팅 메세지 생성

    // 채팅 마지막 메세지 조회
}