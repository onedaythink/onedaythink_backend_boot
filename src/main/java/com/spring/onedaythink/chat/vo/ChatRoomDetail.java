package com.spring.onedaythink.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDetail {
    private int chatRoomNo, findUserNo, toUserOpiNo, fromUserNo;
    private String toNickname, fromNickname, lastMessage, sendNickname,
            creatAt, isClose;


}
