package com.spring.onedaythink.chat.service;

import com.spring.onedaythink.chat.mapper.ChatMapper;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatMapper chatMapper;

    // 마지막 메세지 조회
    @Override
    public ChatMessage getLastMessage(ChatRoom chatRoom) {
         log.debug("getLastMessage");
         return chatMapper.selectLastMessage(chatRoom);
    }

    // 채팅방 종료

    @Override
    public int closeChatRoom(ChatRoom chatRoom) {
        log.debug("closeChatRoom");
        return chatMapper.updateChatRoomClosed(chatRoom);
    }
}
