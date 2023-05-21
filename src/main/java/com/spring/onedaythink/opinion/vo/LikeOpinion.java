package com.spring.onedaythink.opinion.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeOpinion {

    private int userNo;
    private int userOpiNo;
    private String nickname;
}
