package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChat {

    private HaruChatRoomDetail haruChatRoomDetail;
    private HaruChatMessageDetail haruChatMessageDetail;
}
