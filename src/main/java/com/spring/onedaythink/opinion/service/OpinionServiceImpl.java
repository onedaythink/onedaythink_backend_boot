package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.opinion.mapper.OpinionMapper;
import com.spring.onedaythink.opinion.vo.LikeOpinion;
import com.spring.onedaythink.opinion.vo.Opinion;
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
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < opinion.getCreateAt().length(); i++) {
            sb.append(opinion.getCreateAt().charAt(i));
            if (i == 3 || i == 5 ) {
                sb.append("-");
            }
        }
        log.debug(sb.toString());
        opinion.setCreateAt(sb.toString());
        return opinionMapper.selectTodayOpinion(opinion);
    }

    //유저 의견 입력
    @Override
    public int addOpinions(Opinion opinion) {
        int result = opinionMapper.insertOpinion(opinion);
        return result;
    }

    @Override
    public int editOpinions(Opinion opinion) {

        int result = opinionMapper.updateOpinion(opinion);
        return result;
    }

    @Override
    public int deleteOpinions(Opinion opinion) {

        int result = opinionMapper.deleteOpinion(opinion);
        return result;
    }

    @Override
    public List<Opinion> getOtherOpinions(Opinion opinion) {
        List<Opinion> opinionList = opinionMapper.selectAllOtherOpinion(opinion);
        return opinionList;
    }

    @Override
    public int addLikeOpinions(LikeOpinion likeOpinion) {
        int result = opinionMapper.insertLikeOpinion(likeOpinion);
        int count = opinionMapper.getLikeOpinion(likeOpinion);
        return count;
    }

    @Override
    public int DeleteLikeOpinion(LikeOpinion likeOpinion) {
        int result = opinionMapper.deleteLikeOpinion(likeOpinion);
        return result;
    }


}
