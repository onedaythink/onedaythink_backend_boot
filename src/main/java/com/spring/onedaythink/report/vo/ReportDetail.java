package com.spring.onedaythink.report.vo;

import com.spring.onedaythink.report.vo.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDetail {

    private Report report;

    private String  reportContent,
                    userOpinionNo,
                    userNo;


}
