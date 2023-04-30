package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.subject.mapper.SubjectMapper;
import com.spring.onedaythink.subject.vo.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
    public Subject getMainSubject(Subject subject) {
        log.debug("getMainSubject");
        // 오늘날짜의 subDate subject 가 조회되는가?
        Subject mainSubject = subjectMapper.selectSubjectBySubDate(subject);
        // 조회되면 바로 리턴
        if (mainSubject != null) {
            return mainSubject;
        } else {
            // 조회가 안될 경우
            // 1) subject list 중에서 subDate 가 null 인 리스트를 조회
            List<Subject> nullSubjectDateList = subjectMapper.selectNullSubjectDates();
            // 2) 해당 리스트에 담긴 subject 중에서 임의의 1개의 subNo을 추출
            Subject updateSubject = nullSubjectDateList.get(new Random().nextInt(nullSubjectDateList.size()));
            // 3) 추출한 subNo 을 가지고 업데이트를 한 뒤
            updateSubject.setSubDate(subject.getSubDate());
            // 4) subDate 을 가지고 다시 조회를 해서 리턴
            subjectMapper.updateSubjectDate(updateSubject);
            return subjectMapper.selectSubjectBySubDate(subject);
        }
    }

    @Override
    public Subject getSubject(Subject subject) {
        log.debug("getSubject");
        return subjectMapper.selectSubjectBySubNo(subject);
    }

    @Override
    public int deleteSubject(Subject subject) {
        log.debug("deleteSubject");
        return subjectMapper.deleteSubject(subject);
    }

    @Override
    public int updateSubjectDate(Subject subject) {
        log.debug("updateSubjectDate");
        return subjectMapper.updateSubjectDate(subject);
    }

}
