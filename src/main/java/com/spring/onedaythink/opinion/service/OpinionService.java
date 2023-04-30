package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.opinion.vo.Opinion;

import java.util.List;

public interface OpinionService {


    // 의견 전체 조회
    // => 내가 좋아요 누를 의견 조회(동적쿼리)

    // 특정 의견 좋아요 조작(생성/삭제)

    // 나의 의견 전체 조회

    // 나의 의견 조회

    // 나의 의견 생성
    int addOpinions(Opinion opinion);
    // 나의 의견 수정
    int editOpinions(Opinion opinion);
    // 나의 의견 삭제
    int deleteOpinions(Opinion opinion);

    //타인 의견 전체조회
    List<Opinion> getOtherOpinions(Opinion opinion);



}
