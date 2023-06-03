package com.spring.onedaythink.subject.service;

import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.config.UtilLibrary;
import com.spring.onedaythink.subject.mapper.SubjectMapper;
import com.spring.onedaythink.subject.vo.Subject;
import com.spring.onedaythink.subject.vo.SubjectDetail;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class SubjectServiceImpl implements SubjectService {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private SubjectMapper subjectMapper;

    // 논제 추가
    @Override
    public int addSubject(Subject subject) {
        log.debug(subject.getContent());
        String imagePath = subject.getSubImgPath();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(imagePath));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
            String fileNm = now.format(formatter);
            String fileName = fileNm + ".png"; // 파일명에 .png 확장자 추가
            File file = new File("src/main/resources/static/images/" + fileName); // 경로와 파일명을 함께 지정
            ImageIO.write(image, "png", file);

            // Subject 객체의 SubImgPath 필드에 파일 경로 설정
            log.debug(file.getPath());
            log.debug("파일 형식 테스트!!!srcsrc" + file.getPath());

            subject.setSubOriginImg(fileName);
            subject.setSubImgPath(file.getPath());
            subject.setSubDate(new UtilLibrary().createDateFormat("yyyy-MM-dd"));
            log.debug("파일 get형식 3" + file.getPath());
            return subjectMapper.insertSubject(subject);
        } catch (MalformedURLException e) {
            log.error("잘못된 URL입니다.", e);
            throw new IllegalArgumentException("잘못된 URL입니다.", e);
        } catch (IOException e) {
            log.error("이미지 파일을 읽을 수 없습니다.", e);
            throw new IllegalArgumentException("이미지 파일을 읽을 수 없습니다.", e);
        } catch (Exception e) {
            log.error("알 수 없는 오류가 발생했습니다.", e);
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.", e);
        }
    }


    // 논제 전체 조회
    @Override
    public List<Subject> getSubjects() {
        log.debug("getSubject");
        return subjectMapper.selectSubjects();
    }

    // 메인 논제 랜덤 조회
    @Override
    public Subject getMainSubject(Subject subject) {
        log.debug("getMainSubject");
        // 오늘날짜의 subDate subject 가 조회되는가?
        Subject mainSubject = subjectMapper.selectSubjectBySubDate(subject);
        // 조회되면 바로 리턴
        if (mainSubject != null) {
            return mainSubject;
        } else {
            log.debug("-------- 오류 발생");
            log.debug(subject);
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

    // 단일 논제 조회
    @Override
    public Subject getSubject(Subject subject) {
        log.debug("getSubject");
        return subjectMapper.selectSubjectBySubNo(subject);
    }

    // 논제 삭제
    @Override
    public int deleteSubject(SubjectDetail subjectDetail) {
        log.debug("deleteSubject");
        int result = 0;
        for (int subNo : subjectDetail.getSubNoList()){
             result = subjectMapper.deleteSubject(Subject.builder().subNo(subNo).build());
             if (result == 0){
                 return result;
             }
        };
        return result;
    }

    // 논제 수정

    @Override
    public int editSubject(Subject subject) {
        log.debug("editSubject");
        return subjectMapper.updateSubject(subject);

    }
}
