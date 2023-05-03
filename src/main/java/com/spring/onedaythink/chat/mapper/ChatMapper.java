package com.spring.onedaythink.chat.mapper;

import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {


    // 마지막 메세지 조회
    public ChatMessage selectLastMessage(ChatRoom chatRoom);

    // 채팅방 종료
    public int updateChatRoomClosed(ChatRoom chatRoom);

    public int insertChatRoom(ChatRoom chatRoom);

    // 채팅방 전체 조회
    public List<ChatRoomDetail> selectChatRoomByCreateAt(ChatRoomDetail chatRoomDetail);


    public int selectChatRoomCountByUserNo(ChatRoom chatRoom);
}
