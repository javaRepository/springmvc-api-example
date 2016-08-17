package com.company.example.task;

import com.company.example.controller.CommonControllerTest;
import org.junit.Test;

import javax.annotation.Resource;

public class UserSelectTaskJobTest extends CommonControllerTest {

	@Resource
	private UserSelectTaskJob userSelectTaskJob;

	//执行定时器
	@Test
	public void test定时器查询用户() {
		try {
			userSelectTaskJob.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
