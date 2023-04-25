package com.spring.onedaythink.user.Dao;

import com.spring.onedaythink.user.vo.User;

import java.util.List;

public interface UserDao {

    /*
    dao 에서의 crud
     - create ==> insert
     - read ==> select(하나의 객체), selectList(리스트 객체 받아오기)
     - update ==> update
     - delete ==> delete
     - 그 외에는 기능을 확실히 나타낼 수 있는 이름으로.
     */

    // 회원가입 시 아이디 중복 확인
    User userIdCheck(User user);

    // 회원가입 시 닉네임 중복 확인
    User nicknameCheck(User user);

    // 회원 가입
    int insertUser(User user);

    // 로그인
    User loginUser(User user);

    // 회원 정보 조회
    User selectUser(User user);

    // 회원 정보 리스트 조회
    List<User> selectListUsers(User user);

    // 회원 정보 변경
    User updateUser(User user);

    // 회원 탈퇴
    int deleteUser(User user);
}
