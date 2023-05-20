package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.opinion.vo.LikeOpinion;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.opinion.vo.OpinionDetails;

import java.util.List;

public interface OpinionService {


    // 의견 전체 조회
    // => 내가 좋아요 누를 의견 조회(동적쿼리)

    // 특정 의견 좋아요 조작(생성/삭제)

    // 나의 의견 전체 조회
    List<Opinion> getMyOpinion(Opinion opinion);
    // 나의 의견 조회

    // 오늘의 나의 의견 조회
    Opinion getTodayOpinion(Opinion opinion);

    // 나의 의견 생성
    int addOpinions(Opinion opinion);

     //나의 의견 수정
    int updateOpinions(Opinion opinion);

    // 나의 의견 삭제
    int deleteOpinions(Opinion opinion);

    //타인 의견 전체조회
    List<OpinionDetails> getOtherOpinions(OpinionDetails opinionDetails);

    int likeOpinions(LikeOpinion likeOpinion);

//    int addLikeOpinions(LikeOpinion likeOpinion);

//    int DeleteLikeOpinion(LikeOpinion likeOpinion);
}
