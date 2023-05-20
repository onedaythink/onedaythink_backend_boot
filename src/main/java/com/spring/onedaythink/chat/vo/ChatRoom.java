package com.spring.onedaythink.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    private int chatRoomNo, toUserOpiNo, fromUserNo;
    private String chatStatus, creatAt, isClose, fromNickname;

}
