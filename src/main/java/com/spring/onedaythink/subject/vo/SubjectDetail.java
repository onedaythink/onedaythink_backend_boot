package com.spring.onedaythink.subject.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDetail {

    private int subNo;
    private String content, subOriginImg, subImgPath,
                subDate, withdraw;

    private List<Integer> subNoList;

}
