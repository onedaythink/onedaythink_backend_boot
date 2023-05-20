package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedHaruInfoDetail {

    private int chatRoomNo, userNo;
    private String subject;
    private String userMsg;
    private Map<String, String> haruName, haruPrompt, haruOpinion;
    private List<Integer> haruNo;

}

//map<String, Object> randomHaru = new HashMap<>();
//
//randomHaru.put("haruName", dasdada)
