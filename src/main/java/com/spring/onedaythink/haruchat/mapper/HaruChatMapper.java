package com.spring.onedaythink.haruchat.mapper;

import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.haruchat.vo.*;
import com.spring.onedaythink.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HaruChatMapper {

    // 하루봇 랜덤 조회
    public List<HaruChat> selectHaruBot();

    // 채팅 시작
    public int insertHaruChatRoom(User user);

    public HaruChatRoom selectHaruChatRoomNo();

    // 하루봇 채팅방 전체 조회
    public List<HaruChatRoom> selectAllharuChatRoom();

    // 하루봇 채팅방 개별 조회
    public HaruChatRoom selectOneharuChatRoom(HaruChatRoom haruChatRoom);

    // 하루봇 채팅방 직전 대화 10개 조회
    public List<CurrentMsg> selectPreviousMsg(HaruChatRoom haruChatRoom);

    // 하루봇 채팅방 마지막 메세지 조회
    public HaruChatMessage selectLastMessage(HaruChatRoom haruChatRoom);

    // 하루봇 채팅메세지 생성
    public int insertHaruChatMsg(HaruChatMessage haruChatMessage);

    // 하루봇 채팅방 나가기
    public int updateHaruChatRoomClosed(HaruChatRoom haruChatRoom);

    // 채팅방 내 하루봇 의견 조회
    public String selectHaruOpinion(HaruChatRoomDetail haruChatRoomDetail);

    // 하루봇 채팅방 입장시 하루봇들 의견 저장
    public int insertSelectedHaruOpinion(HaruChatMessage haruChatMessageResponse);

    // 하루봇 채팅방 회원별 조회
    public List<HaruChatRoomDetail> selectChatRoomsByUserNo(HaruChatRoomDetail haruChatRoomDetail);

    // 하루봇 개별 조회
    public HaruChat selectHaruBotByHaruNo(HaruChat haruChat);
}
