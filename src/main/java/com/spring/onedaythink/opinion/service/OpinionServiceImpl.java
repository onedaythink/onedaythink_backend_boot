package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.opinion.mapper.OpinionMapper;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

@Service
public class OpinionServiceImpl implements OpinionService{

    private Logger log = LogManager.getLogger("case3");
    @Autowired
    private OpinionMapper opinionMapper;

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
}
