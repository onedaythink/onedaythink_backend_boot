package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.chat.vo.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChatRoomDetail {

    private ChatRoom chatRoom;

    private int haruNo, chatRoomNo;


}