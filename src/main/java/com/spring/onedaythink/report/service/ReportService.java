package com.spring.onedaythink.report.service;

import com.spring.onedaythink.report.vo.Report;

import java.util.List;

public interface ReportService {

    // 신고하기
    int reportInsertUser(Report report);

    // 신고 전체 조회(관리자)
    List<Report> reportSelectAll();

    // 신고 개별 조회(관리자)
    Report reportSelectOne(Report report);


    // 신고처리(관리자)
    Report reportResultUpdate(Report report);

}
