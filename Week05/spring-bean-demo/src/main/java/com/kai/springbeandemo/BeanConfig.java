package com.kai.springbeandemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kaizen
 * @date 2021/09/06
 */
@Configuration
public class BeanConfig {

    @Bean(name = "user")
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setAge(25);
        return user;
    }
}
