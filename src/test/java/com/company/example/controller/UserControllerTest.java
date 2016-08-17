package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.company.example.outerApi.response.AddUserInfoResponse;
import com.company.example.outerApi.response.GetUserInfoResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserControllerTest extends CommonControllerTest {

	//----------------------------------查询用户--------------------------------------

	@Test
	public void test查询用户成功() throws Exception {
		String json = requestContentStringGetWhere("/getUserInfo?username=test123456", new HashMap<>());
		GetUserInfoResponse  response = JSON.parseObject(json, GetUserInfoResponse.class);

		Assert.assertEquals("0", response.getCode());
		Assert.assertTrue("test123456".equals(response.getUsername()));
	}

	@Test
	public void test查询用户参数错误() throws Exception {
		String json = requestContentStringGetWhere("/getUserInfo?username=111112222233333344", new HashMap<>());
		GetUserInfoResponse  response = JSON.parseObject(json, GetUserInfoResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test查询用户参数全为空() throws Exception {
		String json = requestContentStringGetWhere("/getUserInfo", new HashMap<>());
		GetUserInfoResponse  response = JSON.parseObject(json, GetUserInfoResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test查询用户只允许内网访问() throws Exception {
		String json = requestContentStringGet("/getUserInfo", new HashMap<>());
		GetUserInfoResponse response = JSON.parseObject(json, GetUserInfoResponse.class);

		Assert.assertEquals("-8404", response.getCode());
	}

	//----------------------------------添加用户--------------------------------------

	@Test
	public void test添加用户成功() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "123456");
		params.put("userpwd", "123456");
		String json = requestContentStringWhere("/addUserInfo", params);
		AddUserInfoResponse  response = JSON.parseObject(json, AddUserInfoResponse.class);

		Assert.assertEquals("0", response.getCode());
	}

	@Test
	public void test添加用户参数错误() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "111112222233333344");
		params.put("userpwd", "123456");
		String json = requestContentStringWhere("/addUserInfo", params);
		AddUserInfoResponse  response = JSON.parseObject(json, AddUserInfoResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test添加用户userpwd参数为空() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "1010701");
		String json = requestContentStringWhere("/addUserInfo", params);
		AddUserInfoResponse  response = JSON.parseObject(json, AddUserInfoResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test添加用户参数全为空() throws Exception {
		String json = requestContentStringWhere("/addUserInfo", new HashMap<>());
		AddUserInfoResponse  response = JSON.parseObject(json, AddUserInfoResponse.class);

		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test添加用户只允许内网访问() throws Exception {
		String json = requestContentString("/addUserInfo", new HashMap<>());
		AddUserInfoResponse response = JSON.parseObject(json, AddUserInfoResponse.class);

		Assert.assertEquals("-8404", response.getCode());
	}

}
