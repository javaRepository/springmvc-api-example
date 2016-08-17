package com.company.example.exception;

import com.jtool.codegenannotation.CodeGenExceptionDefine;

@CodeGenExceptionDefine(code="-189", desc="解析附件错误，比如.jpg文件上传了.war的内容。" +
        "（也可能是上传超时，因为上传超时就是获得内容为null或不完整，也是会引起解析附件错误）")
public class UploadedFileException extends Exception {
    private static final long serialVersionUID = 3896415268034544833L;
}
