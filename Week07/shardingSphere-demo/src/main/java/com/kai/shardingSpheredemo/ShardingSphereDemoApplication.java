package com.kai.shardingSpheredemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.Date;

@MapperScan(basePackages = "com.kai.shardingSpheredemo")
@SpringBootApplication
public class ShardingSphereDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingSphereDemoApplication.class, args);
	}

}
