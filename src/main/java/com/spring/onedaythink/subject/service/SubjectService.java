package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.subject.vo.Subject;
import com.spring.onedaythink.subject.vo.SubjectDetail;
import com.spring.onedaythink.user.vo.User;

import java.util.List;

public interface SubjectService {

    // 논제 추가
    int addSubject(Subject subject);

    // 논제 전체 조회
    List<Subject> getSubjects();

    // 논제 램덤 조회
    Subject getMainSubject(Subject subject);

    // 논제 단일 조회
    Subject getSubject(Subject subject);

    // 논제 수정
    int editSubject(Subject subject);

    // 논제 삭제
    int deleteSubject(SubjectDetail subjectDetail);




}
