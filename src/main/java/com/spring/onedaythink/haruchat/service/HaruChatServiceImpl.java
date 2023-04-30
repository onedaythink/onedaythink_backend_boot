package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.haruchat.vo.HaruChatRoomDetail;
<<<<<<< HEAD
import com.spring.onedaythink.report.vo.Report;
=======
>>>>>>> 7f292db9435659a08e0854ff7594b6d0e2bb73c7
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HaruChatServiceImpl implements HaruChatService{

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private HaruChatMapper haruChatMapper;

    @Override
    public List<HaruChat> getRandomHaruBot() {
        log.debug("getRandomHaruBot");
        return haruChatMapper.selectHaruBot();
    }

    @Override
    public int createHaruChatRoom(User user) {
        log.debug("createHaruChatRoom");
        return haruChatMapper.insertHaruChatRoom(user);
    }

    @Override
    public HaruChatRoom getHaruChatRoomInfo() {
        log.debug("getHaruChatRoomInfo");
        return haruChatMapper.selectHaruChatRoomNo();
    }

    @Override
    public int selectedHaruBot(HaruChat[] haruChats, HaruChatRoom haruChatRoom) {
        log.debug("selectedHaruBot");
        int resultSum = 0;
        for (int i = 0; i < haruChats.length; i++) {
            int haruNo = haruChats[i].getHaruNo();
            int chatRoomNo = haruChatRoom.getChatRoomNo();
            HaruChatRoomDetail haruChatRoomDetail = HaruChatRoomDetail.builder().haruNo(haruNo).chatRoomNo(chatRoomNo).build();
            resultSum += haruChatMapper.insertHaruChatRoomHaru(haruChatRoomDetail);
        }
        return resultSum;
    }
<<<<<<< HEAD

    // 하루봇 채팅방 전체 조회
    @Override
    public List<HaruChat> selectAllharuChatRoom() {
        return haruChatMapper.selectAllharuChatRoom();
    }
//   // 하루봇 채팅방 개별 조회
//    @Override
//    public HaruChatRoom selectOneHaruChatRoom(HaruChatRoom haruChatRoom) {
//        return haruChatMapper.selectOneharuChatRoom(haruChatRoom);
//    }

    // 하루봇 채팅메세지 생성
    @Override
    public int insertHaruChatMsg(HaruChat haruChat){
        return haruChatMapper.insertHaruChatMsg(haruChat);
    }

}
=======
}
>>>>>>> 7f292db9435659a08e0854ff7594b6d0e2bb73c7
