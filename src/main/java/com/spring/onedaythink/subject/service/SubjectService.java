package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.subject.vo.Subject;

import java.util.List;

public interface SubjectService {

    // 논제 추가
    int addSubject(Subject subject);

    // 논제 전체 조회
    List<Subject> getSubjects();

    // 논제 단일 조회
   Subject getSubject(Subject subject);



}
