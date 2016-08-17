package com.company.example.outerApi.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.jtool.annotation.AvailableValues;
import com.jtool.codegenannotation.CodeGenField;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadSourceRequest {

    @NotNull
    @Size(min = 1, max = 16)
    @CodeGenField("用户名")
    private String username;

    @NotNull
    @AvailableValues(values={"1"})
    @CodeGenField("1：图片<br/>上传资源的类型,图片可用格式为：jpg")
    private String type;

    @NotNull
    @CodeGenField("上传文件, multipart/form-data格式上传文件;")
    @JSONField(serialize=false)
    private MultipartFile file;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UploadSourceRequest{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", file=" + file +
                '}';
    }

}
