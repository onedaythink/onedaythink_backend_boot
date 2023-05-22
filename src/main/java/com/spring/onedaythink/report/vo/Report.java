package com.spring.onedaythink.report.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    private int chatReportNo, chatRoomNo, accusedUserNo, reportNo;
    private String reportResult;


}
