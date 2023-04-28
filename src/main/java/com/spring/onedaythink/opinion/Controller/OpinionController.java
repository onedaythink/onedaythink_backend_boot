package com.spring.onedaythink.opinion.Controller;

import com.spring.onedaythink.opinion.service.OpinionService;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="opinions")
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
        int result = opinionService.addOpinions(opinion);
        return ResponseEntity.ok(result);
    }
}
