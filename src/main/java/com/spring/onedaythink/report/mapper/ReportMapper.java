package com.spring.onedaythink.report.mapper;


import com.spring.onedaythink.report.vo.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    public int insertReport(Report report);

    public List<Report> selectAllReport();

    public Report selectOneReport(Report report);

    public int updateReport(Report report);
}
