package com.spring.onedaythink.opinion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opinion {

    private int userOpiNo,userNo,subNo;
    private String opinion,createAt,isPublic,withdraw;



}