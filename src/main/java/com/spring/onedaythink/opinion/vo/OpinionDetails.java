package com.spring.onedaythink.opinion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpinionDetails {
    private int userOpiNo,userNo,subNo, likeCount;
    private String userId,userImgPath,nickname,status,opinion,
            createAt,isPublic,withdraw;


}
