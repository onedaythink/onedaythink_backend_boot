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

    // 논제 전체 조회
    @GetMapping
    public ResponseEntity getSubject() {
        List<Subject> subjectList = subjectService.getSubject();
        log.debug("getSubject");
        return ResponseEntity.ok(subjectList);
    }

    // 논제 랜덤 조회
    @PostMapping(value = "/main/{subDate}")
    public ResponseEntity mainSubject(@PathVariable String subDate){
        log.debug("mainSubject");
        System.out.println(subDate);
        Subject subject = subjectService.getMainSubject(Subject.builder().subDate(subDate).build());
        return ResponseEntity.ok(subject);
    }

    // 논제 추가
    @PostMapping
    public ResponseEntity addSubject(@RequestBody Subject subject) {
        log.debug("addSubject");
        int result = subjectService.addSubject(subject);
        return ResponseEntity.ok(result);
    }

    // 논제 삭제
    @PostMapping(value="/delete/{subNo}")
    public ResponseEntity deleteSubject(@PathVariable int subNo){
        log.debug("deleteSubject");
        Subject subject = Subject.builder().subNo(subNo).build();
        int result = subjectService.deleteSubject(subject);
        return ResponseEntity.ok(result);

    }


}
