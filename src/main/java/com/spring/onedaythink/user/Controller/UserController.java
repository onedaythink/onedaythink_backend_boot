package com.spring.onedaythink.user.Controller;

import com.spring.onedaythink.user.service.UserService;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(userService.userIdCheck(user));
    }

    @PostMapping(value ="auth/signup/nickname-check" )
    public ResponseEntity<Object> nicknameCheckUser(@RequestBody User user) {
        log.debug("nickname check");
        return ResponseEntity.ok(userService.nicknameCheck(user));
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

    @PostMapping(value = "users")
    public ResponseEntity<Object> editUser(@RequestBody User user){
        log.debug(user);
        User updateUser = userService.editUser(user);
        return ResponseEntity.ok(updateUser);
    }

}
