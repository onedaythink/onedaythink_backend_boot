package com.spring.onedaythink.subject.Controller;

import com.spring.onedaythink.subject.service.SubjectService;
import com.spring.onedaythink.subject.vo.Subject;
import com.spring.onedaythink.subject.vo.SubjectDetail;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class SubjectController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "subjects")
    public ResponseEntity getSubjects() {
        List<Subject> subjectList = subjectService.getSubjects();
        log.debug("getSubjects");
        log.debug(subjectList.get(0));
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
        // 20230522
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
    @PostMapping(value="subjects/delete")
    public ResponseEntity deleteSubject(@RequestBody List<Integer> subNoList){
        log.debug("deleteSubject");
        log.debug(subNoList);
        int result = subjectService.deleteSubject(SubjectDetail.builder().subNoList(subNoList).build());
        return ResponseEntity.ok(result);

    }

    // 논제 수정
    @PostMapping(value="subjects/update/{subNo}")
    public ResponseEntity editSubject(@RequestBody Subject subject){
        log.debug("editSubject");
        int result = subjectService.editSubject(subject);
        return ResponseEntity.ok(result);
    }
}
