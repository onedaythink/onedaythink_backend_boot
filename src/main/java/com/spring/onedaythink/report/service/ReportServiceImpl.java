package com.spring.onedaythink.report.service;

import com.spring.onedaythink.report.mapper.ReportMapper;
import com.spring.onedaythink.report.vo.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ReportMapper reportMapper;

    // 신고하기 생성
    @Override
    public int reportInsertUser(Report report){
        return reportMapper.insertReport(report);
    }

    //신고하기 전체 조회
    @Override
    public List<Report> reportSelectAll(){
        return reportMapper.selectAllReport();
    }

    // 신고하기 개별 조회
    @Override
    public Report reportSelectOne(Report report){
        return reportMapper.selectOneReport(report);
    }

    // 신고하기 수정
    @Override
    public Report reportResultUpdate(Report report){
        int result = reportMapper.updateReport(report);
        log.debug(result);
        return reportMapper.selectOneReport(report);
    }
}
