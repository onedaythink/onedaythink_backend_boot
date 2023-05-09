package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.user.vo.User;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="flask/v1/")
public class FlaskController {

    Logger log = LogManager.getLogger("case3");
    @GetMapping(value="test")
    public ResponseEntity<Object> test() {
        log.debug("test");
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value="post-test")
    public ResponseEntity<Object> test(User user) {
        log.debug("post-test");
        return ResponseEntity.ok(user);
    }
}
