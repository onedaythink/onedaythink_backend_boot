package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.subject.mapper.SubjectMapper;
import com.spring.onedaythink.subject.vo.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public int addSubject(Subject subject) {
        log.debug(subject.getContent());
        return subjectMapper.insertSubject(subject);
    }

    @Override
    public List<Subject> getSubjects() {
        log.debug("getSubject");
        return subjectMapper.selectSubjects();
    }

    @Override
    public Subject getSubject(Subject subject) {
        log.debug("getSubject");
        return subjectMapper.selectSubject(subject);
    }
}
