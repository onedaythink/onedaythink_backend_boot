package com.spring.onedaythink.notify.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notify {
    private int notiNo, userNo;
    private String message, read_yn, create_at;
}
