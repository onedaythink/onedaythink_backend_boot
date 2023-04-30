package com.spring.onedaythink.haruchat.vo;

<<<<<<< HEAD
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
=======
>>>>>>> 7f292db9435659a08e0854ff7594b6d0e2bb73c7
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HaruChat {

<<<<<<< HEAD
    private HaruChatRoomDetail haruChatRoomDetail;
    private HaruChatMessageDetail haruChatMessageDetail;
    private int haruNo;
    private String haruName, haruPrompt, haruOriginImg,
            haruImgPath, status;
}
=======
    private int haruNo;
    private String haruName, haruPrompt, haruOriginImg,
            haruImgPath, status;
}
>>>>>>> 7f292db9435659a08e0854ff7594b6d0e2bb73c7
