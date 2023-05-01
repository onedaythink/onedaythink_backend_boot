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

    // 단일 논제 조회
    public Subject selectSubjectBySubNo(Subject subject);

    // 논제 랜덤 조회
    public int updateSubjectDate(Subject subject);
    public Subject selectSubjectBySubDate(Subject subject);
    public List<Subject> selectNullSubjectDates();

    // 논제 삭제
    public int deleteSubject(Subject subject);

    // 논제 수정
    public int updateSubject(Subject subject);


}
