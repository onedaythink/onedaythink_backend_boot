package com.spring.onedaythink.subject.Controller;

import com.spring.onedaythink.subject.service.SubjectService;
import com.spring.onedaythink.subject.vo.Subject;
import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="subject")
public class SubjectController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private SubjectService subjectService;

//    @GetMapping(value="subject")
//    public ResponseEntity getSubject() {
//        List<Subject> subjectList = subjectService.getSubject();
//        log.debug("getSubject");
//        return ResponseEntity.ok(getSubject);
//    }

    @PostMapping
    public ResponseEntity addSubject(@RequestBody Subject subject) {
        log.debug("addSubject");
        int result = subjectService.addSubject(subject);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value="/{subNo}")
    public ResponseEntity deleteSubject(@RequestBody Subject subject){
        log.debug("deleteSubject");
        int result =0;
        return ResponseEntity.ok("test");

    }

}
