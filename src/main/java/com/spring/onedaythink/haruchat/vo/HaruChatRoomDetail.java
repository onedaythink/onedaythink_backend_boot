package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.chat.vo.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChatRoomDetail {


    private int userNo, chatRoomNo, haruNo;
    private String lastMessage, userNickname, haruName, userImg, haruImg;


}