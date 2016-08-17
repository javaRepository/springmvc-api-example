package com.company.example.exception;

import com.jtool.codegenannotation.CodeGenExceptionDefine;

@CodeGenExceptionDefine(code="-3", desc="参数错误")
public class ParamException extends Exception {
	private static final long serialVersionUID = -69139960778453021L;
	public ParamException(String paramJson) {
		super(paramJson);
	}
}
