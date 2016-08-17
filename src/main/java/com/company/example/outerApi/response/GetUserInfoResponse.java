package com.company.example.outerApi.response;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class GetUserInfoResponse {

    @NotNull
    @CodeGenField("状态码, 0：完成")
    private String code;

    @NotNull
    @CodeGenField("用户名")
    private String username;

    @NotNull
    @CodeGenField("用户密码")
    private String userpwd;

    @NotNull
    @CodeGenField("添加时间")
    private Date addtime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "GetUserInfoResponse{" +
                "code='" + code + '\'' +
                ", username='" + username + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", addtime=" + addtime +
                '}';
    }

}
