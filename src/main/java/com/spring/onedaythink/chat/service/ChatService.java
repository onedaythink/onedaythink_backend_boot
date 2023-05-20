package com.spring.onedaythink.chat.service;

import com.spring.onedaythink.chat.vo.*;
import com.spring.onedaythink.user.vo.User;

import java.util.List;
import java.util.Map;

public interface ChatService {

    /*
     채팅방 리스트 조회
     => 채팅방 대기/활성화/종료 조회(동적쿼리?)
     */
    List<ChatRoomDetail> getChatRooms(ChatRoomDetail chatRoomDetail);

    // 채탕방 생성(대화신청)
    Map<String, Object> addChatRoom(ChatRoom chatRoom);

    // 채팅방 종료
    int closeChatRoom(ChatRoom chatRoom);

    // 챠탕 메세지 전체 조회
    List<ChatMessageDetail> getChatMessageDetails(ChatRoomDetail chatRoomDetail);

    // 채팅 메세지 생성
    int addChatMessage(ChatMessageDetail chatMessageDetail);

    // 채팅 마지막 메세지 조회
    ChatMessage getLastMessage(ChatRoom chatRoom);

    // 채팅방 전체 조회(관리자용)
    List<ChatRoom> getChatRoomsAdmin(ChatRoom chatRoom);

    // 채팅방-유저 신고

    // 신고목록 전체조회

    // 신고 처리

}
