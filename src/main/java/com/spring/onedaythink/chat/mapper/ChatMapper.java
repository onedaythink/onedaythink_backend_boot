package com.spring.onedaythink.chat.mapper;

import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.user.vo.User;
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
    public List<ChatRoomDetail> selectChatRooms(ChatRoomDetail chatRoomDetail);

    public int selectChatRoomCountByUserNo(ChatRoom chatRoom);

    // 채팅룸에 대해서 메세지 디테일들을 모두 조회
    public List<ChatMessageDetail> selectChatMessageDetails(ChatRoomDetail chatRoomDetail);

    // 채팅 메세지를 DB에 추가
    public int insertChatMessage(ChatMessageDetail chatMessageDetail);

    // Admin 전체회원 조회
    public List<ChatRoom> selectListChatRoomsAdmin(ChatRoom chatRoom);

}
