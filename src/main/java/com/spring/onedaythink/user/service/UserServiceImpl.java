package com.spring.onedaythink.user.service;

import com.spring.onedaythink.user.mapper.UserMapper;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userIdCheck(User user) {
        return null;
    }

    @Override
    public User nicknameCheck(User user) {
        return null;
    }

    @Override
    public int registerUser(User user) {
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
        log.debug(user.getUserPwd());
        return userMapper.insertUser(user);
    }

    @Override
    public User loginUser(User user) {
        // login 시 사용하는 id 만 가지고 일단 db 를 불러온 뒤
        User loginUser = userMapper.selectLoginUser(user);

        // 만약 유저 아이디가 일치 하지 않으면 db 에 조회가 안될 것이고,
        if (loginUser == null) {
            log.debug("해당 아이디의 유저가 존재하지 않습니다.");
            return null;
        }
        // 만약 비밀번호가 일치하지 않는다면
        if (!passwordEncoder.matches(user.getUserPwd(), loginUser.getUserPwd())) {
            log.debug("유저가 로그인 창에 입력한 비밀번호를 인코딩한 값 : " + passwordEncoder.encode(user.getUserPwd()));
            log.debug("유저가 로그인 창에 입력한 비밀번호 원본 값 : " + user.getUserPwd());
            log.debug("실제 db에 암호화되서 저장된 비밀번호 값 : " + loginUser.getUserPwd());
            log.debug("비밀번호가 일치하지 않습니다.");
            return null;
        }
        log.debug("로그인에 성공했습니다.");
        return loginUser;
    }

    @Override
    public User passwordCheck(User user) {
        return null;
    }

    @Override
    public User getUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers(User user) {
        return userMapper.selectListUsers(user);
    }

    @Override
    public User editUser(User user) {
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
        userMapper.updateUser(user);
        return userMapper.selectUser(user);
    }

    @Override
    public int withdrawUser(User user) {
        return userMapper.deleteUser(user);
    }
}
