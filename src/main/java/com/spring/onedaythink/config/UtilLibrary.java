package com.spring.onedaythink.config;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class UtilLibrary {

    Logger log = LogManager.getLogger("case3");

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String convertCreateAt(String createAt) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < createAt.length(); i++) {
            sb.append(createAt.charAt(i));
            if (i == 3 || i == 5) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public String createDateFormat(String pattern) {
        Date date = new Date();
        // "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formatDate = sdf.format(date);
        log.debug(formatDate);
        return formatDate;
    }

    // 이미지를 원하는 크기로 리사이징 하는 메소드 초기화 단계
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    // 일반 메소드
    // * 스프링에서 반드시 요청을 처리하는 메소드만 Controller 에 들어가라는 법은 없다!!
    // 현재 넘어온 첨부파일의 이름을 수정 후 서버의 그 폴더에 저장하는 메소드
    // => url 요청을 처리하는 메소드가 아닌 일반 메소드로 만듬
    @SneakyThrows
    public String saveFile(MultipartFile upfile) throws MalformedURLException {

        // 파일명 수정 후 업로드 시키기
        // 예) "flower.png" => "2022061511020112345.png"
        // 1. 원본파일명 가져오기
        String originName = upfile.getOriginalFilename(); // "flower.png"

        // 2. 시간 형식을 문자열로 뽑아내기
        // "20220615110201" (년월일시분초) => SimpleDateFormat
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 3. 뒤에 붙을 5자리 랜덤값 뽑기
        // "13253"
        int ranNum = (int) (Math.random() * 90000 + 10000);

        // 4. 원본파일명으로부터 확장자 부분만 추출
        // ".png"
        String ext = originName.substring(originName.lastIndexOf("."));

        // 5. 확장자명 변경(JPG, JPEG to PNG) 및 크기 조절
        BufferedImage bufferedImage;
        if (ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".jpeg")) {
            ext = ".png";
            bufferedImage = ImageIO.read(upfile.getInputStream());
        } else {
            bufferedImage = ImageIO.read(upfile.getInputStream());
        }
        BufferedImage resizedImage = resizeImage(bufferedImage, 70, 70);  // 이미지 크기 조절
        ImageIO.write(resizedImage, "PNG", new File("src/main/resources/static/profileImages/" + currentTime + ranNum + ext));

        // 6. 모두 이어 붙이기
        String changeName = "src/main/resources/static/profileImages/" + currentTime + ranNum + ext;

        // 7. 경로와 수정파일명을 합체시킨 후 저장
        File file = new File(changeName);

        return changeName;
    }

    // 이미지를 원하는 크기로 리사이징 하는 메소드 실행단계
    public byte[] resizeImage(MultipartFile file, int maxWidth, int maxHeight) throws IOException {
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedImage bufimg = ImageIO.read(bis);

            // Check size of image
            int img_width = bufimg.getWidth();
            int img_height = bufimg.getHeight();
            if (img_width > maxWidth || img_height > maxHeight) {
                float factx = (float) img_width / maxWidth;
                float facty = (float) img_height / maxHeight;
                float fact = (factx > facty) ? factx : facty;
                img_width = (int) ((int) img_width / fact);
                img_height = (int) ((int) img_height / fact);
            }

            // Create a new buffered image with the new dimensions
            BufferedImage resizedImage = new BufferedImage(img_width, img_height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(bufimg, 0, 0, img_width, img_height, null);
            g.dispose();

            // Write the output image to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);
            byte[] bytes = baos.toByteArray();

            return bytes;

        } catch (Exception ex) {
            throw new IOException("An error occured during image resizing.", ex);
        }
    }
}
