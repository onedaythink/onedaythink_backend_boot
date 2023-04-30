package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.service.HaruChatService;
import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.report.vo.Report;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/haruchat")
public class HaruChatController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private HaruChatService haruChatService;

    // 하루봇 랜덤 조회
    @GetMapping
    public ResponseEntity getRandomHaruBot() {
        List<HaruChat> haruBotList = haruChatService.getRandomHaruBot();
        log.debug("getRandomHaruBot");
        return ResponseEntity.ok(haruBotList);
    }

    // 채팅할 하루봇 선택 후 채팅 시작
    @PostMapping(value="/{userNo}")
    public ResponseEntity startHaruChat(@RequestBody HaruChat[] haruChats, @PathVariable int userNo){
        log.debug("startHaruChat");
        User user = User.builder().userNo(userNo).build();
        int result = haruChatService.createHaruChatRoom(user);
        HaruChatRoom haruChatRoom = haruChatService.getHaruChatRoomInfo();
        int resultsum = haruChatService.selectedHaruBot(haruChats, haruChatRoom);
        return ResponseEntity.ok(resultsum);
    }

    // 페르소나봇들과의 채팅방 전체 조회
    @GetMapping(value = "/haruChatAll")
    public ResponseEntity<Object> selectAllharuChatRoom(){
        List<HaruChat> result = haruChatService.selectAllharuChatRoom();
        return ResponseEntity.ok(result);
    }

//    // 페르소나봇들과의 채팅방 개별 조회 GET / haruchat / userNo
//    @GetMapping(value = "/{userNo}")
//    public ResponseEntity<Object> selectOneHaruChatRoom(@PathVariable int userNo) {
//
//        HaruChatRoom haruChatRoom = haruChatService.selectOneHaruChatRoom(HaruChatRoom.builder().userNo(userNo).build());
//        if (haruChatRoom != null) {
//            return ResponseEntity.ok(haruChatRoom);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // 페르소나봇들과의 대화 - 마지막 메시지 조회 GET /haruchat/lastChat/{chatRoomNo}
    //...

    // 페르소나봇들과의 대화 - 메시지 입력 POST /haruchat/{chatRoomNo}
    @PostMapping
    public ResponseEntity<Object> insertHaruChatMsg(@RequestBody HaruChat haruChat){
        log.debug(haruChat);
        int result = HaruChatService.insertHaruChatMsg(haruChat);
        return ResponseEntity.ok(result);
    }
//
//    // 페르소나봇들의 대화 - 대화 나가기 POST(UPDATE) /haruchat/ close/ {chatRoomNo}
//    @PostMapping(value = "/haruChatClose")
//    public ResponseEntity<Object> reportResultUpdate(@RequestBody Report report){
//        Report result = reportService.reportResultUpdate(report);
//        return ResponseEntity.ok(result);
//    }
}