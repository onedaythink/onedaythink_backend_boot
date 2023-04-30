package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaruChat {

    private int haruNo;
    private String haruName, haruPrompt, haruOriginImg,
            haruImgPath, status;
}
