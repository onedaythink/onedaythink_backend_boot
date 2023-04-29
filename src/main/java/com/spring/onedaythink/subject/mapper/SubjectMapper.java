package com.spring.onedaythink.subject.mapper;

import com.spring.onedaythink.subject.vo.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {

    // 논제 추가
    public int insertSubject(Subject subject);

    // 논제 전체 조회
    public List<Subject> selectSubject();

    // 논제 랜덤 조회
    public Subject selectRandomSubject();
    public int updateSubjectDate(Subject subject);

    // 논제 삭제
    public int deleteSubject(Subject subject);

}
