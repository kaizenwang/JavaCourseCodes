package com.kai.springkafkademo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
public class SpringKafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaDemoApplication.class, args);
    }

    @KafkaListener(id = "myId", topics = "test32")
    public void listen(String in) {
        System.out.println(in);
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> template.send("test32", "Hello Kafka");
    }

}
