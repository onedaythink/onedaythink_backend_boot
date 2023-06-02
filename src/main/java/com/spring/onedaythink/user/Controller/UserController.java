package com.spring.onedaythink.user.Controller;

import com.spring.onedaythink.config.UtilLibrary;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.user.service.UserService;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="api/v1/")
public class UserController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private UserService userService;


    @PostMapping(value = "auth")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        User loginUser = userService.loginUser(user);
        log.debug("login test");
        return ResponseEntity.ok(loginUser);
    }

    @GetMapping(value = "admin/users")
    public ResponseEntity<Object> getUsers(){
        List<User> userList = userService.getUsers(null);
        return ResponseEntity.ok(userList);
    }

    @PostMapping(value ="auth/signup/id-check" )
    public ResponseEntity<Object> idCheckUser(@RequestBody User user) {
        log.debug("id check");
        log.debug(user);
        User userCheck = userService.userIdCheck(user);
        log.debug(userCheck);
        return ResponseEntity.ok(userCheck);
    }

    @PostMapping(value ="auth/signup/nickname-check" )
    public ResponseEntity<Object> nicknameCheckUser(@RequestBody User user) {
        log.debug("nickname check");
        return ResponseEntity.ok(userService.nicknameCheck(user));
    }

    @PostMapping(value ="user/newUserUpdate/email-check" )
    public ResponseEntity<Object> emailCheckUser(@RequestBody User user) {
        log.debug("email check");
        return ResponseEntity.ok(userService.emailCheck(user));
    }

    @PostMapping(value = "auth/signup")
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        log.debug(user);
        int result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }


    // 전체회원 조회 (관리자용)
    @GetMapping(value = "admin/usersAdmin")
    public ResponseEntity<Object> getUsersAdmin() {
        List<User> userList = userService.getUsersAdmin(null);
        return ResponseEntity.ok(userList);
    }

    @GetMapping(value = "users/{userNo}")
    public ResponseEntity<Object> getUser(@PathVariable int userNo){
        log.debug(userNo);
        User user = userService.getUser(User.builder().userNo(userNo).build());
        return ResponseEntity.ok(user);
    }

    // 회원 탈퇴(마이페이지)
    @PostMapping(value = "users/{userNo}")
    public ResponseEntity<Object> withdrawUser(@PathVariable int userNo, @RequestBody User user){
        log.debug(user);
        int deleteUser = userService.withdrawUser(user);
        return ResponseEntity.ok(deleteUser);
    }

//    @PostMapping(value = "users")
//    public ResponseEntity<Object> editUser(@RequestBody User user){
//        log.debug(user);
//        User updateUser = userService.editUser(user);
//        return ResponseEntity.ok(updateUser);
//    }

    //나의 공간 - 회원정보수정
    @PostMapping(value="mypage/users/updateprofile")
    public ResponseEntity<Object> mypageUpdateUser(@ModelAttribute User user, @RequestParam(required = false) MultipartFile upfile) throws IOException {
        log.debug("updateOpinions");
        log.debug(user);
        log.debug(upfile);
        // axios 에서 데이터를 전송할 떄, content-type="multipart/form-data" 의 형태로 전송해주어야 한다.

        // 전달된 파일이 있을 경우
        // 1. 파일명 수정 => yyyymmddhhmmssxxxxx.확장자
        // 2. 서버로 업로드
        // 3. 원본명, 서버에 업로드된 수정명, 경로를 db 로 insert
        if(upfile != null && !upfile.getOriginalFilename().equals("")) {

            // saveFile 메소드로 위의 코드를 따로 정의함
            // 필요한 인자로는 업로드한 파일과 webapp 경로를 찾기 위한 request, image/food||bottle 을 구분하는 텍스트
            String changeName = new UtilLibrary().saveFile(upfile);
            user.setUserOriginImg(upfile.getOriginalFilename());
            user.setUserImgPath(changeName);
        }
        // Service 단으로 b 를 넘겨서 insert 요청
        // 넘어온 첨부파일이 있을 경우 b : 제목, 내용, 작성자, 원본파일명, 수정파일명
        // 넘어온 첨부파일이 없을 경우 b : 제목, 내용, 작성자

        log.debug(user);
        User users = userService.editUser(user);
        log.debug(users);
        return ResponseEntity.ok(users);
    }

}
