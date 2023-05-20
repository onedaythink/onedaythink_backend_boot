package com.spring.onedaythink.notify.service;

import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.notify.vo.NotifyDetail;
import com.spring.onedaythink.user.vo.User;

import java.util.List;

public interface NotifyService {

    // 알림 생성
    int addNotify(NotifyDetail notifyDetail);

    // 알림 전체 조회
    List<NotifyDetail> getNotifications(User user);

    // 알림 수정
    int editNotify(NotifyDetail notifyDetail);

    void sendMessage(NotifyDetail notifyDetail);
}
