package com.spring.onedaythink.opinion.service;

import com.spring.onedaythink.opinion.mapper.OpinionMapper;
import com.spring.onedaythink.opinion.vo.Opinion;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpinionServiceImpl implements OpinionService{

    private Logger log = LogManager.getLogger("case3");
    @Autowired
    private OpinionMapper opinionMapper;

    //유저 논제 입력
    @Override
    public int addOpinions(Opinion opinion) {

        int result = opinionMapper.insertOpinion(opinion);
        return result;
    }
}
