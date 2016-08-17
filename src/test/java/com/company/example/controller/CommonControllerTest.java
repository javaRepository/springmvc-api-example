package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.dreamhead.moco.Moco.httpserver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:/application-context.xml"})
public abstract class CommonControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
	//AbstractTransactionalJUnit4SpringContextTests 继承这个类，测试插入到数据库数据会回滚

	private Log log = LogFactory.getLog(this.getClass());

	private Runner runner;

	@Resource
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

		HttpServer server = httpserver(8090);

		runner = Runner.runner(server);
		runner.start();
	}

	@After
	public void after() {
		runner.stop();
	}
	
	protected MockHttpServletRequestBuilder makePostByParams(String uri, Map<String, Object> params) {
		
		List<String> fileKeys = params.keySet().stream().filter(key -> params.get(key) instanceof MockMultipartFile).collect(Collectors.toList());
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder;
		
		if(fileKeys.size() == 0) {
			mockHttpServletRequestBuilder = post(uri);
		} else {
			MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = fileUpload(uri);
			fileKeys.stream().forEach(key -> mockMultipartHttpServletRequestBuilder.file((MockMultipartFile)params.get(key)));
			fileKeys.stream().forEach(key -> params.remove(key));
			mockHttpServletRequestBuilder = mockMultipartHttpServletRequestBuilder;
		}
		
		params.keySet().stream().forEach(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));
		
		System.out.println("request param:" + params);
		
		return mockHttpServletRequestBuilder;
	}

	protected MockHttpServletRequestBuilder makePostByParamsWhere(String uri, Map<String, Object> params) {

		List<String> fileKeys = params.keySet().stream().filter(key -> params.get(key) instanceof MockMultipartFile).collect(Collectors.toList());

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

		if(fileKeys.size() == 0) {
			mockHttpServletRequestBuilder = post(uri).header("where", "kafdoafda#f658adf*2s37^w(ew%43sdf#afdaf2ksijuewehjhk&jihwe9");
		} else {
			MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = fileUpload(uri);
			fileKeys.stream().forEach(key -> mockMultipartHttpServletRequestBuilder.file((MockMultipartFile)params.get(key)));
			fileKeys.stream().forEach(key -> params.remove(key));
			mockHttpServletRequestBuilder = mockMultipartHttpServletRequestBuilder;
		}

		params.keySet().stream().forEach(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));

		System.out.println("request param:" + params);

		return mockHttpServletRequestBuilder;
	}

	protected MockHttpServletRequestBuilder makeGetByParams(String uri, Map<String, Object> params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri);
		params.keySet().stream().map(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));
		
		return mockHttpServletRequestBuilder;
	}

	protected MockHttpServletRequestBuilder makeGetByParamsWhere(String uri, Map<String, Object> params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri).header("where", "kafdoafda#f658adf*2s37^w(ew%43sdf#afdaf2ksijuewehjhk&jihwe9");
		params.keySet().stream().map(e -> mockHttpServletRequestBuilder.param(e, params.get(e).toString()));

		return mockHttpServletRequestBuilder;
	}

	protected MockHttpServletRequestBuilder makeGetByParams2(String uri, String params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri);
		mockHttpServletRequestBuilder.content(params);

		return mockHttpServletRequestBuilder;
	}

	protected String requestContentStringGet2(String uri, String params) throws UnsupportedEncodingException, Exception {
		log.debug("发送地址：" + uri);
		String result = this.mockMvc.perform(makeGetByParams2(uri, params)).andReturn().getResponse().getContentAsString();
		log.debug("返回数据：" + result);
		return result;
	}

	protected String requestContentStringGet(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		log.debug("发送地址：" + uri);
		String result = this.mockMvc.perform(makeGetByParams(uri, innerParams)).andReturn().getResponse().getContentAsString();
		log.debug("返回数据：" + result);
		return result;
	}

	protected String requestContentStringGetWhere(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		log.debug("发送地址：" + uri);
		String result = this.mockMvc.perform(makeGetByParamsWhere(uri, innerParams)).andReturn().getResponse().getContentAsString();
		log.debug("返回数据：" + result);
		return result;
	}

	protected String requestContentString(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		log.debug("发送地址：" + uri);
		String result = this.mockMvc.perform(makePostByParams(uri, innerParams)).andReturn().getResponse().getContentAsString();
		log.debug("返回数据：" + result);
		return result;
	}

	protected String requestContentStringWhere(String uri, Map<String, ?> params) throws UnsupportedEncodingException, Exception {
		Map<String, Object> innerParams = new HashMap<>();
		for(String key : params.keySet()) {
			if(params.get(key) != null) {
				innerParams.put(key, params.get(key));
			}
		}
		log.debug("发送地址：" + uri);
		String result = this.mockMvc.perform(makePostByParamsWhere(uri, innerParams)).andReturn().getResponse().getContentAsString();
		log.debug("返回数据：" + result);
		return result;
	}

	protected String requestCode(String uri, Map<String, Object> params) throws Exception {
		String source = this.mockMvc.perform(makePostByParams(uri, params)).andReturn().getResponse().getContentAsString();
		return JSON.parseObject(source, String.class);
	}
	
	protected void assertCode(String uri, Map<String, Object> params, int code) throws Exception {
		Assert.assertEquals(code, requestCode(uri, params));
	}
	
}
