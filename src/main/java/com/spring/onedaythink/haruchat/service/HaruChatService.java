package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
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

    // 하루봇 채팅방 전체 조회
    List<HaruChatRoom> selectAllharuChatRoom();

   // 하루봇 채팅방 개별 조회
   HaruChatRoom selectOneHaruChatRoom(HaruChatRoom haruChatRoom);

    // 하루봇 채팅방 마지막 메세지 조회
    HaruChatMessage getLastMessage(HaruChatRoom haruChatRoom);

    // 하루봇 채팅메세지 생성
    int insertHaruChatMsg(HaruChatMessage haruChatMessage);

    // 하루봇 채팅방 나가기
    int closeHaruChatRoom(HaruChatRoom haruChatRoom);

}
