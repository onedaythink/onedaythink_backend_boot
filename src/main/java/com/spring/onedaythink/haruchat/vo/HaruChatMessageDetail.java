package com.spring.onedaythink.haruchat.vo;

import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChatMessageDetail {

    private HaruChatMessage HaruChatMessage;

    private int userNo;

}
