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
@RequestMapping(value="api/v1/subject")
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

    @GetMapping(value = "subjects/{subNo}")
    public ResponseEntity getSubject(@PathVariable int subNo) {
        Subject subject = subjectService.getSubject(Subject.builder().subNo(subNo).build());
        log.debug("getSubject");
        return ResponseEntity.ok(subject);
    }

    // 논제 랜덤 조회
    @PostMapping(value = "subjects/main/{subDate}")
    public ResponseEntity mainSubject(@PathVariable String subDate){
        log.debug("mainSubject");
        Subject subject = subjectService.getMainSubject(Subject.builder().subDate(subDate).build());
        return ResponseEntity.ok(subject);
    }


    // 논제 추가
    @PostMapping(value = "subjects")
    public ResponseEntity addSubject(@RequestBody Subject subject) {
        log.debug("addSubject");
        int result = subjectService.addSubject(subject);
        return ResponseEntity.ok(result);
    }

    // 논제 삭제
    @PostMapping(value="subjects/delete/{subNo}")
    public ResponseEntity deleteSubject(@PathVariable int subNo){
        log.debug("deleteSubject");
        Subject subject = Subject.builder().subNo(subNo).build();
        int result = subjectService.deleteSubject(subject);
        return ResponseEntity.ok(result);

    }

}
