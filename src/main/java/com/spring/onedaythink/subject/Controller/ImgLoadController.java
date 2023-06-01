package com.spring.onedaythink.subject.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Controller
@RequestMapping(value = "api/v1/imgfind")
public class ImgLoadController {

    private Logger log = LogManager.getLogger("case3");

    @GetMapping(value = "/subjectImg")
    public String findImg(HttpServletRequest request,
                          HttpServletResponse response, @RequestParam String subjectImgPath) {


        String contentType = URLConnection.guessContentTypeFromName(subjectImgPath);
        response.setContentType(contentType);

        // IMAGE_DIRECTORY 상수는 이미지가 저장된 디렉토리의 경로
        File imageFile = new File(subjectImgPath);
        log.debug("subjectImgPath : " + subjectImgPath);
        log.debug("contentType : " + contentType);

        try (OutputStream out = response.getOutputStream();
             InputStream in = new FileInputStream(imageFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contentType;
    }

}
