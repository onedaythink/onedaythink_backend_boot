package com.spring.onedaythink.subject.mapper;

import com.spring.onedaythink.subject.vo.Subject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubjectMapper {

    public int insertSubject(Subject subject);


}
