package com.spring.onedaythink.chat.mapper;

import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    // 마지막 메세지 조회
    public ChatMessage selectLastMessage(ChatRoom chatRoom);
}
