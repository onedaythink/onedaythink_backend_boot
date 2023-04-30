package com.spring.onedaythink.haruchat.mapper;

import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.haruchat.vo.HaruChatRoomDetail;
import com.spring.onedaythink.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HaruChatMapper {

    // 하루봇 랜덤 조회
    public List<HaruChat> selectHaruBot();

    // 채팅 시작 (사용자 하루봇 선택 반영)
    public int insertHaruChatRoom(User user);
    public HaruChatRoom selectHaruChatRoomNo();
    public int insertHaruChatRoomHaru(HaruChatRoomDetail haruChatRoomDetail);

}
