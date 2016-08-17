package com.company.example.outerApi.request;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetUserInfoRequest {

    @NotNull
    @Size(min = 1, max = 16)
    @CodeGenField("用户名")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GetUserInfoRequest{" +
                "username='" + username + '\'' +
                '}';
    }

}
