package com.spring.onedaythink.notify.service;

import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.user.vo.User;

import java.util.List;

public interface NotifyService {

    // 알림 생성
    int addNotify(Notify notify);

    // 알림 전체 조회
    List<Notify> getNotifications(User user);

    // 알림 수정
    int editNotify(Notify notify);

    public void sendMessage(Notify notify);
}
