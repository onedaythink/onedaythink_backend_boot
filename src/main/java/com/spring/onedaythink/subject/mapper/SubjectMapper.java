package com.spring.onedaythink.subject.mapper;

import com.spring.onedaythink.subject.vo.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {

    // 논제 추가
    public int insertSubject(Subject subject);

    // 논체 전체 조회
    public List<Subject> selectSubjects();

    public Subject selectSubject(Subject subject);


}
