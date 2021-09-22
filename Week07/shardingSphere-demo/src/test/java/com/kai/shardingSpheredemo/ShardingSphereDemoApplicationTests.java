package com.kai.shardingSpheredemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ShardingSphereDemoApplicationTests {

	@Resource
	private UserMapper userMapper;

//	@Test
//	void contextLoads() {
//	}

	@Test
	void testInsert() {
		User user = new User();
		user.setName("张三");
		user.setAvatar("test");
		user.setMobile("13566667777");
		user.setGmtCreate(new Date());
		user.setGmtUpdate(new Date());
		userMapper.insert(user);
	}

	@Test
	void testListAll() {
		List<User> users = userMapper.listAll();
		for (User user : users) {
			System.out.println(user.toString());
		}
	}
}
