package com.spring.onedaythink.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDetail {

    private ChatMessage chatMessage;
    private int chatMsgNo,chatRoomNo,chatSendUserNo;
    private String sendNickname, chatMsgContent, chatCreateAt, chatReadYn, userOriginImg, userImgPath;


}
