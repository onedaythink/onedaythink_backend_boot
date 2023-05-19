package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedHaruInfo {

    private int chatRoomNo, userNo, subNo;
    private String subject;
    private Map<String, String> haruName, haruPrompt;
    private List<Integer> haruNo;

}
