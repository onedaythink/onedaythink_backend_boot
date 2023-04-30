package com.spring.onedaythink.opinion.mapper;

import com.spring.onedaythink.opinion.vo.Opinion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpinionMapper {

    public int insertOpinion(Opinion opinion);

    Opinion selectTodayOpinion(Opinion opinion);
}
