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
    public List<Subject> getSubject() {
        log.debug("getSubject");
        return subjectMapper.selectSubject();
    }

    @Override
    public int deleteSubject(Subject subject) {
        log.debug("deleteSubject");
        return subjectMapper.deleteSubject(subject);
    }

    @Override
    public Subject getMainSubject(Subject subject) {
        log.debug("getMainSubject");
        log.debug(subject);
        Subject mainSubject = subjectMapper.selectRandomSubject(subject);
        System.out.println(mainSubject.getSubDate());
        if( mainSubject.getSubDate()==null || mainSubject.getSubDate().equals("")){
            subjectMapper.updateSubjectDate(mainSubject);
        }
        return mainSubject;
    }
}
