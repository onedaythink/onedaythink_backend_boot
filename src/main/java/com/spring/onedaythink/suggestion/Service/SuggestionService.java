package com.spring.onedaythink.suggestion.Service;

import com.spring.onedaythink.suggestion.vo.Suggestion;

import java.util.List;

public interface SuggestionService {

    // 제안 객체 1개 조회
    Suggestion getSuggestion(Suggestion suggestion);

    // 제안 리스트 객체로 조회
    List<Suggestion> getSuggestions(Suggestion suggestion);

    // 제안 생성
    int addSuggestion(Suggestion suggestion);

    // 제안 수정
    Suggestion editSuggestion(Suggestion suggestion);

    // 제안 삭제(글쓴이/관리자) => 동적쿼리
    int removeSuggestion(Suggestion suggestion);

    // 제안애 댜헌 추천 조작(생성/삭제)

    // 제안에 대한 비추천 조작(생성/삭제)

}
