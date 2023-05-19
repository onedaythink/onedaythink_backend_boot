package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChatMessage {

    private int chatMsgNo, chatRoomNo, chatSendUserNo, chatSendHaruNo, toHaruNo;

    private String chatMsgContent, chatCreateAt, haruImgPath, haruName;
}