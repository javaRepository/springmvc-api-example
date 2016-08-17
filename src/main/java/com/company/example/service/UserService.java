package com.company.example.service;

import com.alibaba.fastjson.JSON;
import com.company.example.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class UserService {

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	private static final String userInfo = "userInfo_";

	private static final Lock lock = new ReentrantLock();

	//------------------------------用户信息---------------------------------------
	public void addUserByRedis(User user) {
		if (user == null) {
			return;
		}

		lock.lock();
		try {
			redisTemplate.opsForValue().set(userInfo + user.getUsername(), JSON.toJSONString(user), 10, TimeUnit.MINUTES);
		} finally {
			lock.unlock();
		}
	}

	public void delUserByRedis(String username) {
		if (username == null) {
			return;
		}

		lock.lock();
		try {
			redisTemplate.delete(userInfo + username);
		} finally {
			lock.unlock();
		}
	}

	public User getUserByRedis(String username) {
		if (username == null) {
			return null;
		}

		lock.lock();
		try {
			User user = null;
			String json = redisTemplate.opsForValue().get(userInfo + username);
			if (null != json) {
				user = JSON.parseObject(json, User.class);
			}

			return user;
		} finally {
			lock.unlock();
		}
	}

}
