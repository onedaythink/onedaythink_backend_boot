package com.spring.onedaythink.subject.Controller;

import com.spring.onedaythink.subject.service.SubjectService;
import com.spring.onedaythink.subject.vo.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/")
public class SubjectController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "subjects")
    public ResponseEntity getSubjects() {
        List<Subject> subjectList = subjectService.getSubjects();
        log.debug("getSubjects");
        return ResponseEntity.ok(subjectList);
    }

    @GetMapping(value = "subjects/{subDate}")
    public ResponseEntity getSubject(@PathVariable String subDate) {
        Subject subject = subjectService.getSubject(Subject.builder().subDate(subDate).build());
        log.debug("getSubject");
        return ResponseEntity.ok(subject);
    }

    @PostMapping(value = "subjects")
    public ResponseEntity addSubject(@RequestBody Subject subject) {
        log.debug("addSubject");
        int result = subjectService.addSubject(subject);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value="subjects/{subNo}")
    public ResponseEntity deleteSubject(@RequestBody Subject subject){
        log.debug("deleteSubject");
        int result =0;
        return ResponseEntity.ok("test");

    }

}
