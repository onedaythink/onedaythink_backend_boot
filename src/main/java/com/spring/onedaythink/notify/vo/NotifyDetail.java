package com.spring.onedaythink.notify.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifyDetail {

    private int notiNo, userNo;
    private String message, read_yn, create_at;

    private int inviteUserNo, lastChatUserNo, chatRoomNo;
    private String inviteNickname, lastChatUserNickname, lastChatMessage, lastChatUserImgPath;

    /*
    알림
    - 채팅 초대가 왔을 때
    : *** 님이 채팅에 초대하셨습니다.
    ==> 유저닉네임. 정확하게는 채팅에 초대한 유저의 번호와 닉네임, 그리고 채팅방 리스트로 이동하기 위한 정보들

    - 채팅 메세지가 전송되었을 때
    : *** : ~~~~~
    ==> 유저닉네임과 마지막 메세지. 그리고 클릭 시 해당 채팅방으로 접속해야 하기 때문에 채팅방 번호

    - 좋아요를 누군가가 눌렀을 때
    : *** 님이 좋아요를 눌렀습니다.

    - 오늘의 메세지를 알려줄 때?
    : 오늘의 사유는 *** 입니다.
   */
}
