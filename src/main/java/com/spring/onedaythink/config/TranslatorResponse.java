package com.spring.onedaythink.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslatorResponse {

    private Message message;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static  class Message {
        private Result result;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        private String srcLangType;
        private String tarLangType;
        private String translatedText;
        private String engineType;
        private String pivot;
        private String dict;
        private String tarDict;
    }

    public String getTranslatedText() {
        return getMessage().getResult().getTranslatedText();
    }
}

