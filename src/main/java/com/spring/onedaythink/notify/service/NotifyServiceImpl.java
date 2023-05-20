package com.spring.onedaythink.notify.service;

import com.spring.onedaythink.notify.mapper.NotifyMapper;
import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.notify.vo.NotifyDetail;
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
    public int addNotify(NotifyDetail notifyDetail) {
        return notifyMapper.insertNotify(notifyDetail);
    }

    @Override
    public List<NotifyDetail> getNotifications(User user) {
        return notifyMapper.selectNotifications(user);
    }

    @Override
    public int editNotify(NotifyDetail notifyDetail) {
        return notifyMapper.editNotify(notifyDetail);
    }

    @Override
    public void sendMessage(NotifyDetail notifyDetail) {
        log.debug("sub test");
        log.debug(notifyDetail);
        // 신규 노티스 생성 및 메세지 발송
        log.debug("/sub/notify/user/" + notifyDetail.getUserNo());
        simpMessagingTemplate.convertAndSend("/sub/notify/users/" + notifyDetail.getUserNo(), notifyDetail);
    }

    @Override
    public NotifyDetail getBeforeNotifyInfo(NotifyDetail notifyDetail) {
        return notifyMapper.selectBeforeNotifyInfo(notifyDetail);
    }
}
