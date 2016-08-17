package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.jtool.codegenannotation.CodeGenExceptionDefine;
import com.jtool.support.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class AppControllerAdvice {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private boolean showErrorStr;

	private static List<Class> exceptionsNotShowTrace = new ArrayList<>();

	//不需要跟踪的错误
	static {
	}

	@ExceptionHandler
	private void processException(HttpServletResponse response, Exception e) throws IOException {
		String eid = MDC.get(LogHelper.JTOOL_LOG_ID);
		//处理日志
		processLog(eid, e);
		//处理返回值
		processResponse(response, eid, e);
	}

	private void processResponse(HttpServletResponse response, String eid, Exception e) throws IOException {

		//必须在response.getWriter()前
		response.setContentType ("application/json; charset=UTF-8");

		try (Writer writer = response.getWriter()) {

			CodeGenExceptionDefine codeGenExceptionDefine = e.getClass().getAnnotation(CodeGenExceptionDefine.class);

			String error_str;

			Map<String, String> result = new HashMap<>();

			if (codeGenExceptionDefine != null) {
				result.put("code", codeGenExceptionDefine.code());
				result.put("eid", eid);
				error_str = codeGenExceptionDefine.desc();
			} else {
				result.put("code", "-99");
				result.put("eid", eid);
				error_str = "未知错误";
			}

			if(showErrorStr){
				result.put("error_str", error_str);
			}

			writer.write(JSON.toJSONString(result));
		}
	}

	private void processLog(String eid, Exception e) {

		CodeGenExceptionDefine codeGenExceptionDefine = e.getClass().getAnnotation(CodeGenExceptionDefine.class);

		if(exceptionsNotShowTrace.contains(e.getClass())) {
			//不需要跟踪的错误，简单输出日志
			if (codeGenExceptionDefine != null) {
				log.debug(codeGenExceptionDefine.code() + "\t" + codeGenExceptionDefine.desc());
			} else {
				log.debug("错误：" + e.getClass() + "\t" + e.getMessage() + "\t" + e.toString());
			}
		} else {
			if (codeGenExceptionDefine != null) {//已知关键错误，显示详细信息
				log.error(codeGenExceptionDefine.code() + "\teid:" + eid, e);
			} else if (e instanceof MultipartException || e instanceof SocketTimeoutException) {
				log.error("上传文件错误或上传超时错误:" + e.getMessage() + "\teid:" + eid);
			} else {
				log.error("未知错误-99\teid:" + eid, e);//输出未知错误日志
			}
		}

	}

}
