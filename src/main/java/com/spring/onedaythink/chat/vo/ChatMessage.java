package com.spring.onedaythink.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    private int chatMsgNo,chatRoomNo,chatSendUserNo;
    private String chatMsgContent, chatCreateAt, chatReadYn, userOriginImg, userImgPath;

}
