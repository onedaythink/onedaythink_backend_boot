package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.subject.vo.Subject;

import java.util.List;

public interface SubjectService {

    // 논제 추가
    int addSubject(Subject subject);

    // 논제 전체 조회
    List<Subject> getSubject();

    // 논제 랜덤 조회




}
