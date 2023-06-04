package com.spring.onedaythink.notify.mapper;

import com.spring.onedaythink.notify.vo.Notify;
import com.spring.onedaythink.notify.vo.NotifyDetail;
import com.spring.onedaythink.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyMapper {
    int insertNotify(NotifyDetail notifyDetail);

    List<NotifyDetail> selectNotifications(User user);

    int editNotify(Notify notify);

    NotifyDetail selectBeforeNotifyInfo(NotifyDetail notifyDetail);

    NotifyDetail selectBeforeNotifyInfoMessage(NotifyDetail notifyDetail);
}
