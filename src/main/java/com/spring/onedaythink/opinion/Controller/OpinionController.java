package com.spring.onedaythink.opinion.Controller;

import com.spring.onedaythink.opinion.service.OpinionService;
import com.spring.onedaythink.opinion.vo.LikeOpinion;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.opinion.vo.OpinionDetails;
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

    //나의 생각 등록 및 수정
    @PostMapping(value="/{userNo}")
    public ResponseEntity<Object> addOpinions(@PathVariable int userNo,@RequestBody Opinion opinion) {
        opinion.setUserNo(userNo);
        log.debug("addOpinions");
        int result = opinionService.addOpinions(opinion);
        return ResponseEntity.ok(result);
    }

    //나의 공간 - 나의 생각 수정
    @PostMapping(value="/mypage/update/{userNo}")
    public ResponseEntity<Object> updateOpinions(@PathVariable int userNo,@RequestBody Opinion opinion) {
        opinion.setUserNo(userNo);
        log.debug("updateOpinions");
        int result = opinionService.updateOpinions(opinion);
        return ResponseEntity.ok(result);
    }

    //나의 생각 의견 삭제
   @PostMapping(value="/mypage/delete/{userNo}")
    public ResponseEntity<Object> deleteOpinions(@PathVariable int userNo, @RequestBody Opinion opinion)
    {
        log.debug("deleteOpinions");
        opinion.setUserNo(userNo);
        int result = opinionService.deleteOpinions(opinion);
        return ResponseEntity.ok(result);
    }

    //타인의 생각조회
    @GetMapping(value = "create-at/{subNo}/{userNo}")
    public ResponseEntity<List<OpinionDetails>> getOtherOpinions(@PathVariable int subNo, @PathVariable int userNo) {

        log.debug("getOtherOpinions");
        List<OpinionDetails> opinionList;
        opinionList = opinionService.getOtherOpinions(OpinionDetails.builder().userNo(userNo).subNo(subNo).build());
        return ResponseEntity.ok(opinionList);
    }

    //타인의 생각 좋아요 컨트롤
    @PostMapping(value ="/like")
    public ResponseEntity<Object> likeOpinions(@RequestBody LikeOpinion likeOpinion) {
        log.debug("like controll test");
        int result = opinionService.likeOpinions(likeOpinion);
        return ResponseEntity.ok(result);
    }
    //나의 공간 - 나의전체조회
    @GetMapping(value ="/mypage/{userNo}")
        public ResponseEntity<Object> getMyOpinion(@PathVariable int userNo) {
        log.debug("getMyOpinion");
        List<Opinion> opinionList;
        opinionList  = opinionService.getMyOpinion(Opinion.builder().userNo(userNo).build());
        return ResponseEntity.ok(opinionList);
        }

    //메인 - 나의 생각 조회
    @GetMapping(value = "{userNo}/{subDate}")
    public ResponseEntity<Object> getTodayOpinion(@PathVariable int userNo, @PathVariable String subDate) {
        log.debug("getOpinion");
        Opinion opinion = opinionService.getTodayOpinion(Opinion.builder().userNo(userNo).createAt(subDate).build());
        log.debug(opinion);
        return ResponseEntity.ok(opinion);
    }

//    @GetMapping(value = "/{subDate}")
//    public ResponseEntity<Object> getOpinionByCreateAt(@PathVariable int userNo, @PathVariable String subDate) {
//        log.debug("getOpinion");
//        Opinion opinion = opinionService.getTodayOpinion(Opinion.builder().userNo(userNo).createAt(subDate).build());
//        log.debug(opinion);
//        return ResponseEntity.ok(opinion);
//    }

}

