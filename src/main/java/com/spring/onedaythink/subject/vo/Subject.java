package com.spring.onedaythink.subject.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    private int subNo;
    private String content, subOriginImg, subImgPath,
            subDate, withdraw;


}
