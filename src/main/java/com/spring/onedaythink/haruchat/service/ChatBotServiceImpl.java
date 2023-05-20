package com.spring.onedaythink.haruchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.*;
import com.spring.onedaythink.user.mapper.UserMapper;
import com.spring.onedaythink.user.vo.User;
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

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ChatBotServiceImpl implements ChatBotService{
    private ScheduledFuture<?> scheduledFuture;
    private Logger log = LogManager.getLogger("case3");
    private ObjectMapper objectMapper = new ObjectMapper();
    @Value("${openai.api-key}")
    private String openaiApiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private boolean hasScheduledTaskExecuted = false;
    @Autowired
    private ScheduledExecutorService executorService;
    @Autowired
    private HaruChatMapper haruChatMapper;
    @Autowired
    private UserMapper userMapper;

    // 하루봇 선택
    @Override
    public List<HaruChat> getRandomHaruBot() {
        log.debug("getRandomHaruBot");
        return haruChatMapper.selectHaruBot();
    }

    // select All chat rooms by userNo
    @Override
    public List<HaruChatRoomDetail> getChatRoomsByUserNo(HaruChatRoomDetail haruChatRoomDetail) {
        log.debug("getChatRoomsByUserNo");
        List<HaruChatRoomDetail> haruChatRoomDetailList = haruChatMapper.selectChatRoomsByUserNo(haruChatRoomDetail);
        for(HaruChatRoomDetail data : haruChatRoomDetailList) {
            if (data.getUserNo() == 0) {
                HaruChat haruChat = haruChatMapper.selectHaruBotByHaruNo(HaruChat.builder().haruNo(data.getHaruNo()).build());
                data.setHaruImg(haruChat.getHaruImgPath());
                data.setHaruName(haruChat.getHaruName());
            } else if (data.getHaruNo() == 0) {
                User user = userMapper.selectUser(User.builder().userNo(data.getUserNo()).build());
                data.setUserImg(user.getUserImgPath());
                data.setUserNickname(user.getNickname());
            }
        }
        return haruChatRoomDetailList;
    }

    // close chat room
    @Override
    public int closeChatRoom(HaruChatRoom haruChatRoom) {
        log.debug("closeChatRoom");
        return haruChatMapper.updateHaruChatRoomClosed(haruChatRoom);
    }

    /** receive first chatbot Response from chatGPT API.**/
    @Override
    public List<HaruChatMessage> getFirstMsgFromChatGPT(SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException {

        log.debug("createHaruChatRoom");
        haruChatMapper.insertHaruChatRoom(selectedHaruInfo);
        HaruChatRoom haruChatRoom = haruChatMapper.selectRecentHaruChatRoomByUserNo(HaruChatRoom.builder().userNo(selectedHaruInfo.getUserNo()).build());
        selectedHaruInfo.setChatRoomNo(haruChatRoom.getChatRoomNo());

        String direction = "[논제]에 대한 [당신의 주장]을 오로지 당신에게 주어진 입장과 특징에 따라 한 문장으로 간결하게 완성하세요. [논제]에서 벗어나지 않아야합니다. 대화체로 대답하세요.";
        String subject = selectedHaruInfo.getSubject();

        Map<String, String> harubotMap = selectedHaruInfo.getHaruPrompt();
        List<HaruChatMessage> responseList = new ArrayList<>();
        for(Map.Entry<String, String> entry : harubotMap.entrySet()){
            log.debug("prompt : [" + entry.getKey() +"] : "+ entry.getValue() );

            String haruPrompt = entry.getValue();

            StringBuilder sb = new StringBuilder();
              sb.append(haruPrompt)
                .append(System.lineSeparator())
                .append(direction)
                .append(System.lineSeparator())
                .append(" [논제] ")
                .append(subject)
                .append(System.lineSeparator())
                .append(" [당신의 주장] ");
            String promptToGPT = sb.toString();

            String answerFromGPT = getChatGptResponse(promptToGPT);
            log.debug("promptToGPT : " + promptToGPT);
            log.debug("answerFromGPT : " + answerFromGPT);


            // Received Message insert : chat room haru table
            // Received Message insert : msg table
            HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
            haruChatMessageResponse.setChatRoomNo(selectedHaruInfo.getChatRoomNo());
            haruChatMessageResponse.setChatSendHaruNo(Integer.parseInt(entry.getKey()));
            haruChatMessageResponse.setChatMsgContent(answerFromGPT);
            HaruChat haruChat = haruChatMapper.selectHaruBotByHaruNo(HaruChat.builder().haruNo(Integer.parseInt(entry.getKey())).build());
            haruChatMessageResponse.setHaruImgPath(haruChat.getHaruImgPath());
            haruChatMessageResponse.setHaruName(haruChat.getHaruName());
            haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);

            haruChatMapper.insertSelectedHaruOpinion(haruChatMessageResponse);

            responseList.add(haruChatMessageResponse);
        }

        return responseList;
    }

    /** chatGPT API Auto call **/
    @Override
    public List<HaruChatMessage> receiveMsgIfNoRequest(SelectedHaruInfoDetail selectedHaruInfoDetail) throws ExecutionException, InterruptedException {

        ScheduledFuture<List<HaruChatMessage>> future = executorService.schedule(() -> {
            log.debug("auto call test");

            // haruNo random choice
            List<Integer> haruNoList = selectedHaruInfoDetail.getHaruNo();
            Random rand = new Random();
            int randomHaruNo = haruNoList.get(rand.nextInt(haruNoList.size()));

            System.out.println(randomHaruNo);

            List<Integer> randomHaru = new ArrayList<>();
            randomHaru.add(randomHaruNo);
            selectedHaruInfoDetail.setHaruNo(randomHaru);

            Map<String, String> map1 = new HashMap<>();
            map1.put(String.valueOf(randomHaruNo), selectedHaruInfoDetail.getHaruName().get(String.valueOf(randomHaruNo)));
            log.debug("####" + selectedHaruInfoDetail.getHaruName().get(String.valueOf(randomHaruNo)));
            selectedHaruInfoDetail.setHaruName(map1);

            Map<String, String> map2 = new HashMap<>();
            map2.put(String.valueOf(randomHaruNo), selectedHaruInfoDetail.getHaruPrompt().get(String.valueOf(randomHaruNo)));
            log.debug("####" + selectedHaruInfoDetail.getHaruPrompt().get(String.valueOf(randomHaruNo)));

            selectedHaruInfoDetail.setHaruPrompt(map2);
            Map<String, String> map3 = new HashMap<>();
            map3.put(String.valueOf(randomHaruNo), selectedHaruInfoDetail.getHaruOpinion().get(String.valueOf(randomHaruNo)));
            selectedHaruInfoDetail.setHaruOpinion(map3);

            // response
            Map<String, String> prompt = generatePrompt(selectedHaruInfoDetail);
            List<HaruChatMessage> responseList = new ArrayList<>();
            for(Map.Entry<String, String> entry : prompt.entrySet()){
                log.debug("+++prompt : [" + entry.getKey() +"] : "+ entry.getValue() );
                String promptToGPT = entry.getValue();
                String answerFromGPT = getChatGptResponse(promptToGPT);
                log.debug("answerFromGPT : " + answerFromGPT);

                // Received Message insert
                HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
                haruChatMessageResponse.setChatRoomNo(selectedHaruInfoDetail.getChatRoomNo());
                haruChatMessageResponse.setChatSendHaruNo(Integer.parseInt(String.valueOf(entry.getKey())));
                haruChatMessageResponse.setChatMsgContent(answerFromGPT);
                haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);
                responseList.add(haruChatMessageResponse);

            }

            hasScheduledTaskExecuted = true;
            return responseList;
        }, 10, TimeUnit.SECONDS);

        List<HaruChatMessage> msgList = future.get();

        return msgList;
    }


    /** receive chatbot Response from chatGPT API.**/
    @Override
    public List<HaruChatMessage> getMsgFromChatGPT(SelectedHaruInfoDetail selectedHaruInfoDetail) {

        hasScheduledTaskExecuted = false;

        // insert user message
        HaruChatMessage userMessage = new HaruChatMessage();
        userMessage.setChatRoomNo(selectedHaruInfoDetail.getChatRoomNo());
        userMessage.setChatSendUserNo(selectedHaruInfoDetail.getUserNo());
        userMessage.setChatMsgContent(selectedHaruInfoDetail.getUserMsg());
        haruChatMapper.insertHaruChatMsg(userMessage);

        // if there is 'request' from user, stop ongoing auto call
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }

        // response
        Map<String, String> prompt = generatePrompt(selectedHaruInfoDetail);
        List<HaruChatMessage> responseList = new ArrayList<>();
        for(Map.Entry<String, String> entry : prompt.entrySet()){
            log.debug("prompt : [" + entry.getKey() +"] : "+ entry.getValue() );
            String promptToGPT = entry.getValue();
            String answerFromGPT = getChatGptResponse(promptToGPT);
            log.debug("answerFromGPT : " + answerFromGPT);

            // Select Haru Info
            HaruChat haruChat = haruChatMapper.selectHaruBotByHaruNo(HaruChat.builder().haruNo(Integer.parseInt(entry.getKey())).build());
            // Received Message insert
            HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
            haruChatMessageResponse.setChatRoomNo(selectedHaruInfoDetail.getChatRoomNo());
            haruChatMessageResponse.setChatSendHaruNo(Integer.parseInt(entry.getKey()));
            haruChatMessageResponse.setHaruName(haruChat.getHaruName());
            haruChatMessageResponse.setHaruImgPath(haruChat.getHaruImgPath());
            haruChatMessageResponse.setChatMsgContent(answerFromGPT);
            haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);
            responseList.add(haruChatMessageResponse);
        }

        // if there is no 'request' from user for 10 seconds
        if (!hasScheduledTaskExecuted) {
            scheduledFuture = executorService.schedule(() -> receiveMsgIfNoRequest(selectedHaruInfoDetail), 10, TimeUnit.SECONDS);
        }
        return responseList;
    }

    // ChatGPT API Call
    private String getChatGptResponse(String prompt){

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
    private Map<String,String> generatePrompt(SelectedHaruInfoDetail selectedHaruInfoDetail) {

        /**
          prompt = [persona explanation] + [direction] + [subject] + [persona's opinion] + [previous dialogue]
         **/

        int chatRoomNo = selectedHaruInfoDetail.getChatRoomNo();
        HaruChatRoom haruChatRoom = HaruChatRoom.builder().chatRoomNo(chatRoomNo).build();

        // direction
        String direction
                = "[논제]에 대한 [당신의 주장]과 [직전 대화]를 참고해서 마지막 말에 이어질 적절한 [대답]을 오로지 당신에게 주어진 입장과 특징에 따라 간결하게 한 문장으로 최대 100자 이내로 완성하세요. [논제]와 [당신의 주장]에서 벗어나지 않아야하며 문장의 끝은 질문 형식으로 마무리 하세요.";
        // previous dialogue
        String currentMsg = "";
        List<CurrentMsg> currentMsgList = haruChatMapper.selectPreviousMsg(haruChatRoom);
        for(int i=currentMsgList.size()-1; i>=0; i--){
            String haruName = currentMsgList.get(i).getHaruName();
            String message = currentMsgList.get(i).getChatMsgContent();
            String fullMsg = "";
            if(currentMsgList.get(i).getChatSendUserNo()==0){
                fullMsg = haruName + " : " + message + " ";
            } else if(currentMsgList.get(i).getChatSendHaruNo()==0){
                fullMsg = "사용자 : " + message+ " ";
            }
            currentMsg = currentMsg.concat(fullMsg);
        }

        // Prompt
//        Map<String, String> promptMap = selectedHaruInfoDetail.getHaruPrompt();
        Map<String, String> promptMap = new HashMap<>();
        for(Map.Entry<String, String> entry : selectedHaruInfoDetail.getHaruPrompt().entrySet()) {
            promptMap.put(entry.getKey(), entry.getValue());
        }

        for (int i = 0; i < selectedHaruInfoDetail.getHaruNo().size(); i++) {

            int haruNo = selectedHaruInfoDetail.getHaruNo().get(i);
            String subject = selectedHaruInfoDetail.getSubject();
            String haruName = selectedHaruInfoDetail.getHaruName().get(haruNo);
            String haruPrompt = selectedHaruInfoDetail.getHaruPrompt().get(String.valueOf(haruNo));

            HaruChatRoomDetail haruChatRoomDetail = new HaruChatRoomDetail();
            haruChatRoomDetail.setChatRoomNo(selectedHaruInfoDetail.getChatRoomNo());
            haruChatRoomDetail.setHaruNo(haruNo);
            String haruOpinion = haruChatMapper.selectHaruOpinion(haruChatRoomDetail);

            StringBuilder sb = new StringBuilder();
            sb.append(haruPrompt)
              .append(System.lineSeparator())
              .append(direction)
              .append(System.lineSeparator())
              .append(" [논제] ")
              .append(subject)
              .append(System.lineSeparator())
              .append(" [당신의 주장] ")
              .append(haruOpinion)
              .append(System.lineSeparator())
              .append(" [직전 대화] ")
              .append(currentMsg)
              .append(System.lineSeparator())
              .append(" [대답] : " );

            String generatedPrompt = sb.toString();

            promptMap.put(String.valueOf(haruNo), generatedPrompt);

        }
        return promptMap;
    }

}
