package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.company.example.outerApi.response.UploadSourceResponse;
import com.company.example.util.MockMultipartFileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.Map;

public class UploadSourceControllerTest extends CommonControllerTest {

	//----------------------------------上传图片--------------------------------------
	@Test
	public void test上传图片参数全为空() throws Exception {
		String json = requestContentString("/uploadSource", new HashMap<>());
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test上传图片type为空() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "test123456");
		String json = requestContentString("/uploadSource", params);
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test上传图片type参数错误() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "test123456");
		params.put("type", "2");
		String json = requestContentString("/uploadSource", params);
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test上传图片file为空() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("username", "test123456");
		params.put("type", "1");
		String json = requestContentString("/uploadSource", params);
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test上传图片内容错误() throws Exception {
		MockMultipartFile jpgFile = MockMultipartFileUtils.makeMockMultipartFile("file", "/media/pic.txt");
		Map<String, Object> params = new HashMap<>();
		params.put("username", "test123456");
		params.put("type", "1");
		params.put(jpgFile.getName(), jpgFile);
		String json = requestContentString("/uploadSource", params);
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("-3", response.getCode());
	}

	@Test
	public void test上传图片成功() throws Exception {
		MockMultipartFile jpgFile = MockMultipartFileUtils.makeMockMultipartFile("file", "/media/1.jpg");
		Map<String, Object> params = new HashMap<>();
		params.put("username", "test123456");
		params.put("type", "1");
		params.put(jpgFile.getName(), jpgFile);
		String json = requestContentString("/uploadSource", params);
		UploadSourceResponse response = JSON.parseObject(json, UploadSourceResponse.class);
		Assert.assertEquals("0", response.getCode());
	}

}
