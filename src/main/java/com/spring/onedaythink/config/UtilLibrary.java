package com.spring.onedaythink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UtilLibrary {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String convertCreateAt(String createAt) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < createAt.length(); i++) {
            sb.append(createAt.charAt(i));
            if (i == 3 || i == 5 ) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

}
