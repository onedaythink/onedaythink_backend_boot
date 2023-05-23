package com.spring.onedaythink.user.service;

import com.spring.onedaythink.user.vo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    /*
     controller 와 service 에서의 crud
     - create ==> add, register
     - read ==> get(단일 객체 받아오기), gets(리스트 객체 받아오기)
     - update ==> edit
     - delete ==> remove or withdraw
     */

    // 회원가입 시 아이디 중복 확인
    User userIdCheck(User user);

    // 회원가입 시 닉네임 중복 확인
    User nicknameCheck(User user);

    // 회원 가입
    int registerUser(User user);

    // 로그인
    User loginUser(User user);

    // 회원 정보 조회를 위한 비밀번호 확인
    User passwordCheck(User user);

    // 회원 정보 조회
    User getUser(User user);

    // 회원 정보 리스트 조회
    List<User> getUsers(User user);

    // Admin 회원 정보 리스트 조회
    List<User> getUsersAdmin(User user);


    // 회원 정보 변경
    User editUser(User user);

    // 회원 탈퇴
    int withdrawUser(User user);

    // mypage 유저정보 업데이트
    int mypageUpdateUser(User user);

}
