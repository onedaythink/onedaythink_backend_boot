package com.spring.onedaythink.opinion.Controller;

import com.spring.onedaythink.opinion.service.OpinionService;
import com.spring.onedaythink.opinion.vo.Opinion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/v1/opinions")
public class OpinionController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private OpinionService opinionService;

//나의 생각 입력'
    @PostMapping(value="/{userNo}")
    public ResponseEntity<Object> addOpinions(@PathVariable int userNo,@RequestBody Opinion opinion)
    {
        log.debug("opinion test");
        opinion.setUserNo(userNo);
        log.debug(opinion);
        int result = opinionService.addOpinions(opinion);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{userNo}/{subDate}")
    public ResponseEntity<Object> getTodayOpinion(@PathVariable int userNo, @PathVariable String subDate) {
        log.debug("getOpinion");
        Opinion opinion = opinionService.getTodayOpinion(Opinion.builder().userNo(userNo).createAt(subDate).build());
        log.debug(opinion);
        return ResponseEntity.ok(opinion);
    }

    @GetMapping(value = "/{subDate}")
    public ResponseEntity<Object> getOpinionByCreateAt(@PathVariable int userNo, @PathVariable String subDate) {
        log.debug("getOpinion");
        Opinion opinion = opinionService.getTodayOpinion(Opinion.builder().userNo(userNo).createAt(subDate).build());
        log.debug(opinion);
        return ResponseEntity.ok(opinion);
    }

}
