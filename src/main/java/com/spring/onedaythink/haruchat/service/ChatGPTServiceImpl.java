package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGPTServiceImpl implements ChatGPTService{

    private Logger log = LogManager.getLogger("case3");
    private ObjectMapper objectMapper = new ObjectMapper();
    @Value("${openai.api-key}")
    private String openaiApiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Autowired
    private HaruChatMapper haruChatMapper;

    @Override
    public HaruChatMessage getChatGPTResponse(HaruChatMessage haruChatMessage) {

        log.debug("getChatGPTResponse"+openaiApiKey);
        // API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+openaiApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);
        ArrayList<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "assistant");
        message.put("content", haruChatMessage.getChatMsgContent());
        messages.add(message);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);


//         RESPONSE 수신
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

        Map<String, Object> responseBody = response.getBody();

        List<Map<String, Object>> completions = (List<Map<String, Object>>) responseBody.get("choices");
        Map<String, Object> firstCompletion = completions.get(0);
        String generatedText = (String)((Map<String, Object>) firstCompletion.get("message")).get("content");


        log.debug(generatedText);

        // 받아온 메시지 insert
        HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
        haruChatMessageResponse.setChatRoomNo(haruChatMessage.getChatRoomNo());
        haruChatMessageResponse.setChatMsgContent(generatedText);
        haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);

        return haruChatMessageResponse;
    }

    // NAVER translation test
    @Override
    public String getTranslatedPrompt(HaruChatMessage haruChatMessage) throws JsonProcessingException {

        /** prompt = haruPrompt + summary + currentMsg **/

        // DB 에서 문자열 데이터 가져오기
        int chatRoomNo = haruChatMessage.getChatRoomNo();
        String haruPrompt = haruChatMapper.selectOneHaruBot(haruChatMessage.getToHaruNo());
        String summary = haruChatMapper.selectSummary(chatRoomNo);
        Map<String, String> currentMsg = new HashMap<>();
        for(int i=0; i<5; i++){
            HaruChatMessage haruChatCurrentMsg =
            if(HaruChatMessage.)
        }

        // Papago 에 문자열 넘겨줘서 번역된 내용 리턴받기
        String clientId = "s8eK4gzT5u70EsNAfYPT";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "1im3PL2SN2";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String textTemp;
        try {
            textTemp = URLEncoder.encode("안녕하세요. 오늘 기분은 어떻습니까?", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = papago_post(apiURL, requestHeaders, textTemp);

        TranslatorResponse translatorResponse = objectMapper.readValue(responseBody, TranslatorResponse.class);
        log.debug(translatorResponse.getTranslatedText());

        return translatorResponse.getTranslatedText();
    }


    private static String papago_post(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=ko&target=en&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}


