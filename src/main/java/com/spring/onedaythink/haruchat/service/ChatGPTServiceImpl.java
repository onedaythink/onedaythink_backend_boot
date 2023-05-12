package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onedaythink.config.TranslatorResponse;
import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ChatGPTServiceImpl implements ChatGPTService{

    private ScheduledFuture<?> scheduledFuture;
    private Logger log = LogManager.getLogger("case3");
    private ObjectMapper objectMapper = new ObjectMapper();
    @Value("${openai.api-key}")
    private String openaiApiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Autowired
    private ScheduledExecutorService executorService;
    @Autowired
    private HaruChatMapper haruChatMapper;


    /** api auto call test **/
//    @Override
//    public String testAPIAutoCall() {
//        // API Request
//        String text = "how are you?";
//        String generatedText = getChatGpt(text);
//        log.debug(generatedText);
//
//        return generatedText;
//    }

    /**  async test **/
    @Async
    @Override
    public Future<List<HaruChatMessage>> someMethod(SelectedHaruInfo selectedHaruInfo){

        ScheduledFuture<List<HaruChatMessage>> future = executorService.schedule(() -> {
            try {
                List<HaruChatMessage> list = getChatGPTResponse(selectedHaruInfo);
                HaruChatMessage haruChatMessage = list.get(0);
                String response = haruChatMessage.getChatMsgContent();
                log.debug("auto call test : " + response);
                return list;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }, 10, TimeUnit.SECONDS);

        return future;
    }

    /** receive chatbot Response from papago API & chatGPT API.**/
    @Async
    @Override
    public List<HaruChatMessage> getChatGPTResponse(SelectedHaruInfo selectedHaruInfo) throws JsonProcessingException {

        // async test
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
        // Translate Generated Prompt
        Map<String, String> prompt = generatePrompt(selectedHaruInfo);
        for(Map.Entry<String, String> entry : prompt.entrySet()){
            log.debug("prompt : " + entry.getKey() +" : "+ entry.getValue() );
        }
        // Response
        List<HaruChatMessage> responseList = new ArrayList<>();
        Set<String> keySet = prompt.keySet();
        for(String key : keySet) {
            String translatedPrompt= getTranslatedTextKoToEn(prompt.get(key));

            String generatedText = getChatGpt(translatedPrompt);

            String answerFromGPT = getTranslatedTextEnToKo(generatedText);
            log.debug("answerFromGPT : " + answerFromGPT);

            // Received Message insert
            HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
            haruChatMessageResponse.setChatRoomNo(selectedHaruInfo.getChatRoomNo());
            haruChatMessageResponse.setChatMsgContent(answerFromGPT);
            haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);
            responseList.add(haruChatMessageResponse);
        }

        // async test
        scheduledFuture = executorService.schedule(() -> someMethod(selectedHaruInfo), 10, TimeUnit.SECONDS);
        return responseList;
    }

    // ChatGPT API call
    private String getChatGpt(String prompt){

        // API Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);
        ArrayList<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "assistant");
        message.put("content", prompt);
        messages.add(message);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // API response
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

        Map<String, Object> responseBody = response.getBody();

        List<Map<String, Object>> completions = (List<Map<String, Object>>) responseBody.get("choices");
        Map<String, Object> firstCompletion = completions.get(0);
        String generatedText = (String) ((Map<String, Object>) firstCompletion.get("message")).get("content");

        return generatedText;
    }

    // Generate Prompt
    /** prompt = (chatGPTID) + fixedStatement + (subject) + (haruPrompt) + (summary/summarizedCount) + currentMsg **/
    private Map<String,String> generatePrompt(SelectedHaruInfo selectedHaruInfo) {

        // Data
        int chatRoomNo = selectedHaruInfo.getChatRoomNo();
        String prompt = "";
        String fixedStatementForPersona = "자기소개를 하지 마십시오. 제한된 길이의 답변을 원하고, 최대 250자까지만 답변하십시오";
        String direction = "[이전 대화 요약문]과 [직전 대화]를 참고해서 [사용자]의 마지막 말에 대한 적절한 대답을 이 사람의 입장에서 할 수 있는 대답을 완성하세요. 문장의 끝은 사용자에 대한 질문으로 마무리 하세요.";

        HaruChatRoom haruChatRoom = HaruChatRoom.builder().chatRoomNo(chatRoomNo).build();
        int notSummarized = haruChatMapper.selectSummerized(haruChatRoom);
        String summary = "";
        if(notSummarized>=10){
          summary = summmarizeDialogue(selectedHaruInfo);
        }

        String currentMsg = "";
        List<CurrentMsg> currentMsgList = haruChatMapper.selectPreviousMsg(haruChatRoom);
        for(int i=0; i<currentMsgList.size(); i++){
            String haruName = currentMsgList.get(i).getHaruName();
            String message = currentMsgList.get(i).getChatMsgContent();
            String fullMsg = "";
            if(currentMsgList.get(i).getChatSendUserNo()==0){
                fullMsg = "[" + haruName + "]" + " : " + message + " ";
            } else if(currentMsgList.get(i).getChatSendHaruNo()==0){
                fullMsg = "[ 사용자 ] : " + message+ " ";
            }
            currentMsg = currentMsg.concat(fullMsg);
        }

        // Prompt
        Map<String, String> promptMap = new HashMap<>();
        for (int i = 0; i < selectedHaruInfo.getHaruNo().size(); i++) {
            // DATA
            int haruNo = selectedHaruInfo.getHaruNo().get(i);
            String subject = selectedHaruInfo.getSubject();
            String haruPrompt = selectedHaruInfo.getHaruPrompt().get(String.valueOf(haruNo));
            String prefix = haruPrompt + fixedStatementForPersona;

            // Generate Prompt

            if (haruChatMapper.selectOneSummary(haruChatRoom).getSummary() == null ) {
                if (notSummarized == 0) {
                    // First Answer From GPT( No Summary, No PreviousDialogue )
                    prompt = prefix +" [논제]에 대해 이 사람의 입장에서 할 수 있는 대답을 완성하세요." + " [논제] " + subject ;
                } else if (notSummarized < 10) {
                    // No Summary & small amount of previousDialogue data
                    prompt = prefix + direction + " [논제] " + subject + " [이전 대화 기록] " + currentMsg;
                } else {
                    // No Summary & full amount of previousDialogue data
                    prompt = prefix + direction + " [요약문] " + summary + " [이전 대화 기록] " + currentMsg ;
                }
            } else if (haruChatMapper.selectOneharuChatRoom(haruChatRoom).getSummary()!=null) {
                String previousSummary = haruChatMapper.selectOneharuChatRoom(haruChatRoom).getSummary();
                if (notSummarized < 10) {
                    // Summary existed & small amount of previousDialogue data
                    prompt = prefix + direction + " [요약문] " + previousSummary + " [이전 대화 기록] " + currentMsg ;
                } else {
                    // Summary existed & full amount of previousDialogue data
                    prompt = prefix + direction + " [요약문] " + previousSummary + " [이전 대화 기록] " + currentMsg ;

                }
            }



            String haruName = selectedHaruInfo.getHaruName().get(String.valueOf(haruNo));
            promptMap.put(haruName, prompt);

        }

        return promptMap;
    }
    //                    String chatGptId = haruChatMapper.selectOneHarubotIdWithNo
//                            (ChatGPTId.builder().haruNo(haruNo).chatRoomNo(selectedHaruInfo.getChatRoomNo()).build());
//                    if (chatGptId == null) {
//                        map.put(String.valueOf(i), prompt);
//                    } else {
//                        map.put(chatGptId, prompt);
//                    }
    // generate summary from ChatGPT API
    private String summmarizeDialogue(SelectedHaruInfo selectedHaruInfo){

        // Data ;  sendUserNo, sendHaruNo(haruName), chatMsgContent (order ; MsgNo);
        // Form ; [previousSummary] + [haruName/UserNo] : "chatMsgContent"

        HaruChatRoom haruChatRoom
                = haruChatMapper.selectOneharuChatRoom
                ( HaruChatRoom.builder().userNo(selectedHaruInfo.getUserNo()).build());

        String previousSummary = haruChatRoom.getSummary();
        String currentMsg = "";
        List<CurrentMsg> currentMsgList = haruChatMapper.selectPreviousMsg(haruChatRoom);
        for(int i=0; i<currentMsgList.size(); i++){
            String haruName = currentMsgList.get(i).getHaruName();
            String message = currentMsgList.get(i).getChatMsgContent();
            String fullMsg = "";
            if(currentMsgList.get(i).getChatSendUserNo()==0){
                fullMsg = "[" + haruName + "]" + " : " + message + " ";
            } else if(currentMsgList.get(i).getChatSendHaruNo()==0){
                fullMsg = "[ 사용자 ] : " + message+ " ";
            }
            currentMsg = currentMsg.concat(fullMsg);
        }

        String prompt = "[이전 대화 요약문]과 [이어진 대화]를 참고해서 전체 대화를 이해할 수 있는 요약문을 만들어줘. "+
        "[이전 대화 요약문] : " + previousSummary + "[이어진 대화] " + currentMsg;

        String summarizedText = getChatGpt(prompt);

        return summarizedText;
    }

    // NAVER PAPAGO translation test
    // 한국어 -> 영어
    private String getTranslatedTextKoToEn(String text) throws JsonProcessingException {

        // Papago 에 문자열 넘겨줘서 번역된 내용 리턴받기
        String clientId = "xnT47tpDHBjrpfmRxGzO";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "iH_0PB2f51";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String textTemp;
        try {
            textTemp = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = papago_post_ko_en(apiURL, requestHeaders, textTemp);

        TranslatorResponse translatorResponse = objectMapper.readValue(responseBody, TranslatorResponse.class);
        log.debug("translation : " +translatorResponse.getTranslatedText());

        return translatorResponse.getTranslatedText();
    }

    // 영어 -> 한국어
    private String getTranslatedTextEnToKo(String text) throws JsonProcessingException {

        // Papago 에 문자열 넘겨줘서 번역된 내용 리턴받기
        String clientId = "xnT47tpDHBjrpfmRxGzO";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "iH_0PB2f51";//애플리케이션 클라이언트 시크릿값";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = papago_post_en_ko(apiURL, requestHeaders, text);

        TranslatorResponse translatorResponse = objectMapper.readValue(responseBody, TranslatorResponse.class);
        log.debug("translation : " +translatorResponse.getTranslatedText());

        return translatorResponse.getTranslatedText();
    }

    // 한국어 -> 영어
    private static String papago_post_ko_en(String apiUrl, Map<String, String> requestHeaders, String text){
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

    // 영어 -> 한국어
    private static String papago_post_en_ko(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
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

    // HTTP Connection
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

    // Response Reading
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


