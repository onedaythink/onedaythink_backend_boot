package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.haruchat.vo.HaruChatRoomDetail;
import com.spring.onedaythink.haruchat.vo.HaruChatMessageDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HaruChat {

    private HaruChatRoomDetail haruChatRoomDetail;
    private HaruChatMessageDetail haruChatMessageDetail;

    private int haruNo;
    private String haruName, haruPrompt, haruOriginImg,
            haruImgPath, status;


}
