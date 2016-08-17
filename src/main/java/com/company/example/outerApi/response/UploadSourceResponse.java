package com.company.example.outerApi.response;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;

public class UploadSourceResponse {

    @NotNull
    @CodeGenField("状态码, 0：正确")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UploadSourceResponse{" +
                "code='" + code + '\'' +
                '}';
    }

}
