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

    // Admin 전체회원 조회
    @GetMapping(value = "admin/usersAdmin")
    public ResponseEntity<Object> getUsersAdmin(){
        List<User> userList = userService.getUsersAdmin(null);
        return ResponseEntity.ok(userList);
    }

    @PostMapping(value = "auth/signup")
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        int result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }

}
