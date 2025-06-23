package org.springboot.questionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestionServiceApplication {

    /*
     * application.properties has settings for enabling
     * Eureka Client, so other registered MicroServices
     * Can access the required methods through OpenFeign
     */
    public static void main(String[] args) {
        SpringApplication.run(QuestionServiceApplication.class, args);
    }

}
