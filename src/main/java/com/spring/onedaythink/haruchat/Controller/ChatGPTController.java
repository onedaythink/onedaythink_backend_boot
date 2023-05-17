package com.spring.onedaythink.haruchat.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.service.ChatGPTService;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value="api/v1/haruchat")
public class ChatGPTController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatGPTService chatGPTService;

//    @GetMapping(value = "/test")
//    public ResponseEntity<Object> socketApiTest() {
//        log.debug("socketApiTest");
//        return ResponseEntity.ok("hi");
//    }
    /**
     * receive chatbot Response from papago API & chatGPT API.
     **/
    @PostMapping("/chatgpt/response")
    public ResponseEntity<Object> receiveMsgFromChatGPT(@RequestBody SelectedHaruInfo selectedHaruInfo) throws JsonProcessingException {
        log.debug("getChatGPTResponse");
        List<HaruChatMessage> list
                = chatGPTService.getChatGPTResponse(selectedHaruInfo);

        return ResponseEntity.ok(list);
    }

    @PostMapping("/test")
    public ResponseEntity<Object> testAPIAutoCall(@RequestBody SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException {
        log.debug("testAPIAutoCall");
        Future<List<HaruChatMessage>> future = chatGPTService.someMethod(selectedHaruInfo);
        List<HaruChatMessage> list = future.get();
        String msg = list.get(0).getChatMsgContent();
        return ResponseEntity.ok("성공"+msg);
    }

    /** api 자동 call test **/
//    @PostMapping("/test")
//    @Scheduled(fixedRate=2000)
//    public ResponseEntity<Object> testAPIAutoCall(){
//        log.debug("testAPIAutoCall");
//        String response = chatGPTService.testAPIAutoCall();
//        return ResponseEntity.ok(response);
//    }

}

