package com.spring.onedaythink.chat.service;

import com.spring.onedaythink.chat.vo.Chat;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;

public interface ChatService {

    /*
     채팅방 리스트 조회
     => 채팅방 대기/활성화/종료 조회(동적쿼리?)
     */

    // 채탕방 생성(대화신청)

    // 채탕벙 수정(대기에서 수락/거절/종료)

    // 채팅방 종료
    int closeChatRoom(ChatRoom chatRoom);

    // 챠탕 메세지 전체 조회

    // 채팅 메세지 생성

    // 채팅 마지막 메세지 조회
    ChatMessage getLastMessage(ChatRoom chatRoom);

    // 채팅방-유저 신고

    // 신고목록 전체조회

    // 신고 처리

}
