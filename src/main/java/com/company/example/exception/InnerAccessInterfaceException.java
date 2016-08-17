package com.company.example.exception;

import com.jtool.codegenannotation.CodeGenExceptionDefine;

@CodeGenExceptionDefine(code="-8404", desc="内部接口不允许外网访问")
public class InnerAccessInterfaceException extends Exception {
    private static final long serialVersionUID = -6812600147636270811L;
}
