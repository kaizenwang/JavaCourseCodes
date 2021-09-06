package com.kai.springbeandemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("springbean.xml")
public class SpringBeanDemoApplication {

    @Autowired
    private User user;
    @Autowired
    private Student student;
    @Autowired
    private Role role;

    public static void main(String[] args) {
        SpringApplication.run(SpringBeanDemoApplication.class, args);
    }

    @Bean
    public void printUserInfo() {
        System.out.println("user info: id:" + user.getId() + " name:" + user.getName() + " age:" + user.getAge());
    }

    @Bean
    public void printStudentInfo() {
        System.out.println(student.studentInfo());
    }

    @Bean
    public void printRole() {
        role.printInfo();
    }
}
