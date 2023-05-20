package com.spring.onedaythink.notify.Controller;


import com.spring.onedaythink.notify.service.NotifyService;
import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.notify.vo.NotifyDetail;
import com.spring.onedaythink.notify.vo.NotifyDetails;
import com.spring.onedaythink.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotifyController {

    private Logger log = LogManager.getLogger("case3");

    private final SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NotifyService notifyService;

    @GetMapping(value = "api/v1/notify/users/{userNo}")
    public ResponseEntity<Object> getNotifies(@PathVariable int userNo) {
        List<NotifyDetail> notifies = notifyService.getNotifications(User.builder().userNo(userNo).build());
        return ResponseEntity.ok(notifies);
    }

    @PostMapping(value ="api/v1/notify/edit/users" )
    public void editNotify(@RequestBody NotifyDetails notifyDetail) {
        log.debug(notifyDetail);
    }

    @MessageMapping(value = "/notify/enter")
    public void enter(User user){
//        log.debug(user);
//        log.debug("연결");
//        log.debug("/sub/notify/user/"+user.getUserNo());
        simpMessagingTemplate.convertAndSend("/sub/notify/users/"+user.getUserNo(), user);
    }

}
