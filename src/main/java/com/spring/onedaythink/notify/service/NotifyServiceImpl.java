package com.spring.onedaythink.notify.service;

import com.spring.onedaythink.notify.mapper.NotifyMapper;
import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService{

    Logger log = LogManager.getLogger("case3");

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public int addNotify(Notify notify) {
        return notifyMapper.insertNotify(notify);
    }

    @Override
    public List<Notify> getNotifications(User user) {
        return notifyMapper.selectNotifications(user);
    }

    @Override
    public int editNotify(Notify notify) {
        return notifyMapper.editNotify(notify);
    }

    @Override
    public void sendMessage(Notify notify) {
        log.debug("sub test");
        log.debug(notify);
        // 신규 노티스 생성 및 메세지 발송
        log.debug("/sub/notify/user/" + notify.getUserNo());
        simpMessagingTemplate.convertAndSend("/sub/notify/user/" + notify.getUserNo(), notify);
    }
}
