package com.spring.onedaythink.chat.service;

import com.spring.onedaythink.chat.mapper.ChatMapper;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.notify.service.NotifyService;
import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService{

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private NotifyService notifyService;

    // 채팅방 조회
    @Override
    public List<ChatRoomDetail> getChatRooms(ChatRoomDetail chatRoomDetail) {
        return chatMapper.selectChatRooms(chatRoomDetail);
    }

    // 채팅방 개설
    @Override
    public Map<String, Object> addChatRoom(ChatRoom chatRoom){
        log.debug("add ChatRoom");
        Map<String, Object> map = new HashMap<>();

        // 기존에 채팅룸이 열려있는지 확인
        int result = chatMapper.selectChatRoomCountByUserNo(chatRoom);
        // 채팅룸이 없다면 추가
        if (result == 0) {
            result = chatMapper.insertChatRoom(chatRoom);
            map.put("msg", "채팅창을 개설했습니다.");
            Notify notify = Notify.builder()
                            .userNo(chatRoom.getFromUserNo())
                            .message(chatRoom.getFromNickname() + "님이 채팅에 초대하셨습니다.")
                            .build();
            int notifyResult = notifyService.addNotify(notify);
            notifyService.sendMessage(notify);
        } else {
            map.put("msg", "이미 개설된 채팅방이 존재합니다.");
        }
        return map;
    }

    // 채팅방 종료
    @Override
    public int closeChatRoom(ChatRoom chatRoom) {
        log.debug("closeChatRoom");
        return chatMapper.updateChatRoomClosed(chatRoom);
    }

    // 마지막 메세지 조회
    @Override
    public ChatMessage getLastMessage(ChatRoom chatRoom) {
         log.debug("getLastMessage");
         return chatMapper.selectLastMessage(chatRoom);
    }

    @Override
    public List<ChatMessageDetail> getChatMessageDetails(ChatRoomDetail chatRoomDetail){
        return chatMapper.selectChatMessageDetails(chatRoomDetail);
    }

    @Override
    public int addChatMessage(ChatMessageDetail chatMessageDetail){
        return chatMapper.insertChatMessage(chatMessageDetail);
    }

    // admin 전체 회원 조회
    @Override
    public List<ChatRoom> getChatRoomsAdmin(ChatRoom chatRoom) {
        return chatMapper.selectListChatRoomsAdmin(chatRoom);
    }

}
