package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChatMessageDetail {

    private String chatMsgContent, chatCreateAt ;
    private int chatMsgNo, chatSendHaruNo, chatSendUserNo;




}
