package com.company.example.outerApi.request;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddUserInfoRequest {

    @NotNull
    @Size(min = 1, max = 16)
    @CodeGenField("用户名")
    private String username;

    @NotNull
    @Size(min = 1, max = 16)
    @CodeGenField("用户密码")
    private String userpwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    @Override
    public String toString() {
        return "AddUserInfoRequest{" +
                "username='" + username + '\'' +
                ", userpwd='" + userpwd + '\'' +
                '}';
    }

}
