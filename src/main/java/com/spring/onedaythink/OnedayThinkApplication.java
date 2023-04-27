package com.spring.onedaythink;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnedayThinkApplication {

    private static Logger log = LogManager.getLogger("case3");
    public static void main(String[] args) {

        log.debug("start");
        SpringApplication.run(OnedayThinkApplication.class, args);
    }

}
