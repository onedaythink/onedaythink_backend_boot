package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedChar {

    private int haruNo, userNo, chatRoomNo;
    private String haruChatRoomDetail, haruImgPath, haruName, haruOriginImg, haruPrompt, status;
}
