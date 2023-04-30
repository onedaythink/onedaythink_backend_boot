package com.spring.onedaythink.opinion.Controller;

import com.spring.onedaythink.opinion.service.OpinionService;
import com.spring.onedaythink.opinion.vo.Opinion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        opinion.setUserNo(userNo);
        int result = opinionService.addOpinions(opinion);
        return ResponseEntity.ok(result);
    }
    //나의 생각 수정
    @PutMapping(value="/{userNo}")
    public ResponseEntity<Object> editOpinions(@PathVariable int userNo,@RequestBody Opinion opinion)
    {
        opinion.setUserNo(userNo);
        int result = opinionService.editOpinions(opinion);
        return ResponseEntity.ok(result);
    }
    //나의 생각 삭제
   @DeleteMapping(value="/{userNo}")
    public ResponseEntity<Object> deleteOpinions(@PathVariable int userNo, @RequestBody Opinion opinion)
    {
        opinion.setUserNo(userNo);
        int result = opinionService.deleteOpinions(opinion);
        return ResponseEntity.ok(result);
    }

    //타인의 생각조회
    @GetMapping(value = "/{subNo}")
    public ResponseEntity<List<Opinion>> getOtherOpinions(@PathVariable int subNo) {
        List<Opinion> opinionList;
        opinionList = opinionService.getOtherOpinions(Opinion.builder().subNo(subNo).build());
        return ResponseEntity.ok(opinionList);
    }

    //타인의 생각 좋아요
    @PostMapping(value ="/like/{userNo}/{userOpiNo}")
    public ResponseEntity<Object> addLikeOpinion(@PathVariable int userNo,@PathVariable int userOpiNo)
    {
        likeOpinion.set(userNo);
        likeOpinion.set(userOpiNo);
        int result = opinionService.addLikeOpinions(likeOpinion);
        return ResponseEntity.ok(result);
    }
    //타인의 생각 좋아요 취소

    //나의 공간 - 나의전체조회

    //메인 - 나의 생각 조회
}
