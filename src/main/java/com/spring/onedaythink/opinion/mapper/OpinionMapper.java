package com.spring.onedaythink.opinion.mapper;

import com.spring.onedaythink.opinion.vo.LikeOpinion;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.opinion.vo.OpinionDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpinionMapper {
    //나의 의견 생성
    public int insertOpinion(Opinion opinion);

    //나의 의견 수정
    public int updateOpinion(Opinion opinion);

    //나의 의견 삭제
    public int deleteOpinion(Opinion opinion);

    //타인의 의견 전체조회
    public List<OpinionDetails> selectAllOtherOpinion(OpinionDetails opinionDetails);

    // 메인
    Opinion selectTodayOpinion(Opinion opinion);

    // Mypage 의견 조회
    public List<Opinion> selectAllMyOpinion(Opinion opinion);

    public int insertLikeOpinion(LikeOpinion likeOpinion);

    int deleteLikeOpinion(LikeOpinion likeOpinion);

//    int getLikeOpinion(LikeOpinion likeOpinion);

    int selectLikeOption(LikeOpinion likeOpinion);
}
