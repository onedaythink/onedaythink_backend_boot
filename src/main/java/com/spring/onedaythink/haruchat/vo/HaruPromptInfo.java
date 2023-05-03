package com.spring.onedaythink.haruchat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HaruPromptInfo {

    private int haruNo;
    private String haruName, haruPrompt, chatGptId;
}
