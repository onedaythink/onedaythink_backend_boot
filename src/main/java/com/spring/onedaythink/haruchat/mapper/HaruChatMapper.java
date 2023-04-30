package com.spring.onedaythink.haruchat.mapper;

import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.haruchat.vo.HaruChatRoomDetail;
import com.spring.onedaythink.report.vo.Report;
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

    // 하루봇 채팅방 전체 조회
    public List<HaruChat> selectAllharuChatRoom();

//    // 하루봇 채팅방 개별 조회
//    public HaruChatRoom selectOneharuChatRoom(HaruChatRoom haruChat);

    // 하루봇 채팅메세지 생성
    public int insertHaruChatMsg(HaruChat haruChat);
}