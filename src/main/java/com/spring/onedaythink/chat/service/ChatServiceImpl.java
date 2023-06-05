package com.spring.onedaythink.chat.service;

import com.spring.onedaythink.chat.mapper.ChatMapper;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.config.UtilLibrary;
import com.spring.onedaythink.notify.service.NotifyService;
import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.notify.vo.NotifyDetail;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        log.debug(chatRoom);
        Map<String, Object> map = new HashMap<>();

        // 기존에 채팅룸이 열려있는지 확인
        int result = chatMapper.selectChatRoomCountByUserNo(chatRoom);
        // 채팅룸이 없다면 추가
        if (result == 0) {
            chatRoom.setCreateAt(new UtilLibrary().createDateFormat("yyyy-MM-dd HH:mm:ss"));
            result = chatMapper.insertChatRoom(chatRoom);
            map.put("msg", "채팅방이 개설되었습니다.");
            // userOpi 정보를 가지고 글 쓴 유저의 유저의 정보를 가지고 와야 한다.
            NotifyDetail notifyDetail = notifyService.getBeforeNotifyInfo(NotifyDetail.builder().
                    userOpiNo(chatRoom.getToUserOpiNo()).
                    inviteUserNo(chatRoom.getFromUserNo()).
                    type("invite").
                    build());
            log.debug("add chatroom notify : " + notifyDetail);
            notifyDetail.setCreateAt(new UtilLibrary().createDateFormat("yyyy-MM-dd HH:mm:ss"));
            notifyDetail.setMessage(chatRoom.getFromNickname() + "님이 채팅에 초대하셨습니다.");
            notifyDetail.setType("invite");
            notifyDetail.setInviteNickname(chatRoom.getFromNickname());
            int notifyResult = notifyService.addNotify(notifyDetail);
            log.debug("add chatroom notify : " + notifyDetail);
            notifyService.sendMessage(notifyDetail);
        } else {
            map.put("msg", "이미 상대방과의 채팅방이 존재합니다.");
        }
        return map;
    }

    // 채팅방 종료
    @Override
    public int closeChatRoom(ChatRoom chatRoom) {
        log.debug(chatRoom);
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
        chatMessageDetail.setChatCreateAt(new UtilLibrary().createDateFormat("yyyy-MM-dd HH:mm:ss"));
        int result = chatMapper.insertChatMessage(chatMessageDetail);

        //     메세지 생성 후 대상에게 전송
        NotifyDetail notifyDetail = notifyService.getBeforeNotifyInfoMessage(NotifyDetail.builder().
                chatRoomNo(chatMessageDetail.getChatRoomNo()).
                type("message").
                build());

        log.debug(notifyDetail);
        // 알림 유저 번호와 챗메세지의 전송 유저 번호가 동일하다면?
        // 챗 개설자가 아닌 챗 초대자가 메세지를 보낸 것
        if( notifyDetail.getUserNo() == chatMessageDetail.getChatSendUserNo()) {
            notifyDetail.setUserNo(notifyDetail.getFromUserNo());
            notifyDetail.setInviteNickname(notifyDetail.getToNickname());
        } else {
            notifyDetail.setInviteNickname(notifyDetail.getFromNickname());
        }
        notifyDetail.setMessage(chatMessageDetail.getSendNickname() + " : " + chatMessageDetail.getChatMsgContent());
        notifyDetail.setType("message");
        log.debug(notifyDetail);
        int notifyResult = notifyService.addNotify(notifyDetail);
        notifyService.sendMessage(notifyDetail);
        return result;
    }

    // admin 전체 회원 조회
    @Override
    public List<ChatRoom> getChatRoomsAdmin(ChatRoom chatRoom) {

        return chatMapper.selectListChatRoomsAdmin(chatRoom);
    }

}