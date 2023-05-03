package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentMsg {

    private int chatSendUserNo, chatSendHaruNo, toHaruNo;

    private String chatMsgContent, HaruName;

}
