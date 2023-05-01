package com.spring.onedaythink.report.mapper;


import com.spring.onedaythink.report.vo.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    // 신고하기 생성
    public int insertReport(Report report);

    // 신고하기 전체 조회
    public List<Report> selectAllReport();

    // 신고하기 개별 조회
    public Report selectOneReport(Report report);

    // 신고하기 결과 처리
    public int updateReport(Report report);
}
