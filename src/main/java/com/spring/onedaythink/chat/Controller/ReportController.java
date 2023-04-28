package com.spring.onedaythink.chat.Controller;

import com.spring.onedaythink.report.service.ReportService;
import com.spring.onedaythink.report.vo.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chatting/report")
public class ReportController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ReportService reportService;

    // 신고하기 생성
    @PostMapping
    public ResponseEntity<Object> reportUser(@RequestBody Report report){
        log.debug(report);
        int result = reportService.reportInsertUser(report);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/reportAll")
    public ResponseEntity<Object> reportSelectAll(){
        List<Report> result = reportService.reportSelectAll();
        return ResponseEntity.ok(result);
    }
    // 신고하기 개별조회(관리자)
    @GetMapping(value = "/{chatReportNo}")
    public ResponseEntity<Object> reportSelectOne(@PathVariable("chatReportNo") int chatReportNo) {

        // Report.builder().chatReportNo(chatReportNo).build()
        // Report report = new Report()
        // report.setChatReportNo(~~~)

        Report report = reportService.reportSelectOne(Report.builder().chatReportNo(chatReportNo).build());
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
//            return ResponseEntity
            return ResponseEntity.ok(report);
        }
    }

    // 신고하기 결과처리(관리자)
    @PostMapping(value = "/reportPut")
    public ResponseEntity<Object> reportResultUpdate(@RequestBody Report report){
        Report result = reportService.reportResultUpdate(report);
        return ResponseEntity.ok(result);
    }

}
