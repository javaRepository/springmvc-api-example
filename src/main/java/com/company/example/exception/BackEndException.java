package com.company.example.exception;

import com.jtool.codegenannotation.CodeGenExceptionDefine;

@CodeGenExceptionDefine(code="-98", desc="后端错误")
public class BackEndException extends Exception {
    private static final long serialVersionUID = 4928417405583486806L;
}
