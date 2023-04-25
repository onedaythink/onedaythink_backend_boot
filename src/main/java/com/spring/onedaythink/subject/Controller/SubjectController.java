package com.spring.onedaythink.subject.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/v1/")
public class SubjectController {

    private Logger log = LogManager.getLogger("case3");

    @GetMapping(value="subject")
    public ResponseEntity getSubject() {
        log.debug("getSubject");
        return ResponseEntity.ok("test");
    }

}
