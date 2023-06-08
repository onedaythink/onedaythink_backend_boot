package com.spring.onedaythink.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService{

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 문자열로 변환하기
    public <T> String convertToString(T data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(data);
            return value;
        } catch(Exception e){
            log.error(e);
            return null;
        }
    }

    // 객체로 변환하기
    public <T> Optional<T> convertToObject(String data, Class<T> classType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(data, classType));
        } catch(Exception e){
            log.error(e);
            return Optional.empty();
        }
    }

//    @GetMapping(value="redis/chat/rooms/{chatRoomNo}/messages")
//    void testHash(@PathVariable int chatRoomNo) {
//
//        ChatMessage chatMessage = ChatMessage.builder().
//                chatMsgNo(12221).chatSendUserNo(5).chatMsgContent("죽을 것 같아").build();
//        redisTemplate.opsForZSet().add("ChatRoomNo_"+String.valueOf(chatRoomNo), convertToString(chatMessage), 1);
//
//        ChatMessage chatMessage2 = ChatMessage.builder().
//                chatMsgNo(11).chatSendUserNo(5).chatMsgContent("죽을 것 같아dddds").build();
//
//        redisTemplate.opsForZSet().add("ChatRoomNo_"+String.valueOf(chatRoomNo), convertToString(chatMessage2), 2);
//
//
//        String key = "ChatRoomNo_"+String.valueOf(chatRoomNo);
//
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//        Set<String> redisData = zSetOps.reverseRange("ChatRoomNo_" + String.valueOf(chatRoomNo), 0, -1);
//        if (redisData != null && !redisData.isEmpty()) {
//            for (String data : redisData) {
//                Optional<ChatMessage> result = convertToObject(data, ChatMessage.class);
//                if (result.isPresent()) {
//                    ChatMessage chatMessage3 = result.get();
//                    log.debug(chatMessage3);
//                    // Use the chatMessage object here
//                } else {
//                    // Handling for failed conversion
//                }
//            }
//        } else {
//            // Handling for empty Redis data
//        }
//    }

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

        /*
        * redis 에 있던 모든 데이터들을 일괄적으로 rdb 에 저장하기
        * 그 후 redis 의 데이터를 지우기
        * */

        return chatMapper.updateChatRoomClosed(chatRoom);
    }

    // 마지막 메세지 조회
    @Override
    public ChatMessage getLastMessage(ChatRoom chatRoom) {
        log.debug("getLastMessage");

        /*
        * redis 의 가장 마지막 데이터 조회
        * */

        String key = "ChatRoomNo_"+String.valueOf(chatRoom.getChatRoomNo());
        List<ChatMessageDetail> msgList = new ArrayList<>();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String redisData = listOps.index("ChatRoomNo_" + String.valueOf(chatRoom.getChatRoomNo()), -1);
        if (redisData != null && !redisData.isEmpty()) {
            Optional<ChatMessageDetail> msg = convertToObject(redisData, ChatMessageDetail.class);
            log.debug("redis test");
            ChatMessageDetail cmd = msg.get();
            return ChatMessage.builder().
                    chatRoomNo(cmd.getChatRoomNo()).
                    chatMsgContent(cmd.getChatMsgContent()).
                    chatCreateAt(cmd.getChatCreateAt()).
                    userOriginImg(cmd.getUserOriginImg()).
                    userImgPath(cmd.getUserImgPath()).
                    build();
        } else {
            log.debug("rdb test");
            return chatMapper.selectLastMessage(chatRoom);
        }
    }

    // 메세지 전체 조회
    @Override
    @Cacheable(value = "ChatMessageDetail", cacheManager = "contentCacheManager")
    public List<ChatMessageDetail> getChatMessageDetails(ChatRoomDetail chatRoomDetail){
        String key = "ChatRoomNo_"+String.valueOf(chatRoomDetail.getChatRoomNo());
        List<ChatMessageDetail> msgList = new ArrayList<>();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<String> redisData = listOps.range("ChatRoomNo_" + String.valueOf(chatRoomDetail.getChatRoomNo()), 0, -1);
        if (redisData != null && !redisData.isEmpty()) {
            for (String data : redisData) {
                Optional<ChatMessageDetail> result = convertToObject(data, ChatMessageDetail.class);
                if (result.isPresent()) {
                    msgList.add(result.get());
                    // Use the chatMessage object here
                } else {
                    break;
                }
            }
            log.debug("redis test");
            return msgList;
        } else {
            log.debug("rdb test");
            return chatMapper.selectChatMessageDetails(chatRoomDetail);
        }
    }

    // 메세지 작성
    @Override
    public int addChatMessage(ChatMessageDetail chatMessageDetail){
        chatMessageDetail.setChatCreateAt(new UtilLibrary().createDateFormat("yyyy-MM-dd HH:mm:ss"));
        /*
        * redis 의 chatRoom 에 해당하는 redis zset 에 추가
        * */
        redisTemplate.opsForList().rightPush("ChatRoomNo_"+String.valueOf(chatMessageDetail.getChatRoomNo()), convertToString(chatMessageDetail));

        // RDB mysql
        int result = chatMapper.insertChatMessage(chatMessageDetail);

        //     메세지 생성 후 대상에게 전송
//        NotifyDetail notifyDetail = notifyService.getBeforeNotifyInfoMessage(NotifyDetail.builder().
//                chatRoomNo(chatMessageDetail.getChatRoomNo()).
//                type("message").
//                build());
//
//        log.debug(notifyDetail);
//        // 알림 유저 번호와 챗메세지의 전송 유저 번호가 동일하다면?
//        // 챗 개설자가 아닌 챗 초대자가 메세지를 보낸 것
//        if( notifyDetail.getUserNo() == chatMessageDetail.getChatSendUserNo()) {
//            notifyDetail.setUserNo(notifyDetail.getFromUserNo());
//            notifyDetail.setInviteNickname(notifyDetail.getToNickname());
//        } else {
//            notifyDetail.setInviteNickname(notifyDetail.getFromNickname());
//        }
//        notifyDetail.setMessage(chatMessageDetail.getSendNickname() + " : " + chatMessageDetail.getChatMsgContent());
//        notifyDetail.setType("message");
//        log.debug(notifyDetail);
//        int notifyResult = notifyService.addNotify(notifyDetail);
//        notifyService.sendMessage(notifyDetail);
        return 1;
    }

    // admin 전체 회원 조회
    @Override
    public List<ChatRoom> getChatRoomsAdmin(ChatRoom chatRoom) {

        return chatMapper.selectListChatRoomsAdmin(chatRoom);
    }

}