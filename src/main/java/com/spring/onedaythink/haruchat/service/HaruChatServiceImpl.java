package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.haruchat.vo.HaruChatRoomDetail;
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
}
