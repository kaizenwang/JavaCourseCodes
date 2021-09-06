package com.kai.springbeandemo;

import org.springframework.stereotype.Component;

/**
 * @author kaizen
 * @date 2021/09/06
 */
@Component
public class Student {

    public String studentInfo() {
        return "student name: 李四， className: 大三班";
    }
}
