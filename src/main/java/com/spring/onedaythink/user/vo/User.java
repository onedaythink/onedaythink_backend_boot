package com.spring.onedaythink.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int userNo;
    private String userId, userPwd, gender, birth,
            userName, nickname, email, userImg,
            blackList, status, userType;

}
