package com.spring.onedaythink.suggestion.Dao;

import com.spring.onedaythink.suggestion.vo.Suggestion;

import java.util.List;

public interface SuggestionDao {

    // 제안 객체 1개 조회
    Suggestion selectSuggestion(Suggestion suggestion);

    // 제안 리스트 객체로 조회

    List<Suggestion> selectListSuggestions(Suggestion suggestion);

    // 제안 생성
    int insertSuggestion(Suggestion suggestion);

    // 제안 수정
    Suggestion updateSuggestion(Suggestion suggestion);

    // 제안 삭제
    int deleteSuggestion(Suggestion suggestion);

}
