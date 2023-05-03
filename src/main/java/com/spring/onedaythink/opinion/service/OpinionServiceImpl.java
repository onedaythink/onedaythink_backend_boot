package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.config.UtilLibrary;
import com.spring.onedaythink.opinion.mapper.OpinionMapper;
import com.spring.onedaythink.opinion.vo.LikeOpinion;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.opinion.vo.OpinionDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class OpinionServiceImpl implements OpinionService{

    private Logger log = LogManager.getLogger("case3");
    @Autowired
    private OpinionMapper opinionMapper;

    @Override
    public List<Opinion> getMyOpinion(Opinion opinion) {
        log.debug("getMyOpinion");
        List<Opinion> opinionList = opinionMapper.selectAllMyOpinion(opinion);
        return opinionList;
    }

    @Override
    public Opinion getTodayOpinion(Opinion opinion) {
        String changeCreateAt = new UtilLibrary().convertCreateAt(opinion.getCreateAt());
        opinion.setCreateAt(changeCreateAt);
        return opinionMapper.selectTodayOpinion(opinion);
    }

    //유저 의견 입력
    @Override
    public int addOpinions(Opinion opinion) {
        int result = 0;
        String changeCreateAt = new UtilLibrary().convertCreateAt(opinion.getCreateAt());
        opinion.setCreateAt(changeCreateAt);
        Opinion myOpinion = opinionMapper.selectTodayOpinion(opinion);
        // 조회가 안되면 생성
        if (myOpinion == null) {
            result = opinionMapper.insertOpinion(opinion);
        }
        // 조회가 되면 업데이트
        else {
            result = opinionMapper.updateOpinion(opinion);
        }
        return result;
    }


    @Override
    public int deleteOpinions(Opinion opinion) {

        int result = opinionMapper.deleteOpinion(opinion);
        return result;
    }

    @Override
    public List<OpinionDetails> getOtherOpinions(OpinionDetails opinionDetails) {
        List<OpinionDetails> opinionDetailsListList = opinionMapper.selectAllOtherOpinion(opinionDetails);
        return opinionDetailsListList;
    }

    @Override
    public int likeOpinions(LikeOpinion likeOpinion) {
        int result = 0;
        // 좋아요를 눌렀던 적이 있는지 확인.
        int cnt = opinionMapper.selectLikeOption(likeOpinion);
        // 없다면, 좋아요를 생성
        if (cnt == 0) {
            result = opinionMapper.insertLikeOpinion(likeOpinion);
        } else {
            // 있다면, 좋아요를 삭제
            result = opinionMapper.deleteLikeOpinion(likeOpinion);
        }
        return result;
    }

//    @Override
//    public int addLikeOpinions(LikeOpinion likeOpinion) {
//        int result = opinionMapper.insertLikeOpinion(likeOpinion);
//        int count = opinionMapper.getLikeOpinion(likeOpinion);
//        return count;
//    }
//
//    @Override
//    public int DeleteLikeOpinion(LikeOpinion likeOpinion) {
//        int result = opinionMapper.deleteLikeOpinion(likeOpinion);
//        return result;
//    }


}
