package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.company.example.exception.BackEndException;
import com.company.example.exception.ParamException;
import com.company.example.exception.UploadedFileException;
import com.company.example.outerApi.request.UploadSourceRequest;
import com.company.example.outerApi.response.UploadSourceResponse;
import com.jtool.codegenannotation.CodeGenApi;
import com.jtool.codegenannotation.CodeGenRequest;
import com.jtool.codegenannotation.CodeGenResponse;
import com.jtool.support.log.LogHelper;
import com.jtool.validator.ParamBeanValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Controller
public class UploadSourceController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private String uploadImgPath;

    @CodeGenApi(name = "(外)上传图片", docSeq = 3.0, description = "上传图片")
    @CodeGenRequest(UploadSourceRequest.class)
    @CodeGenResponse(UploadSourceResponse.class)
    @RequestMapping(value = "/uploadSource", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String uploadSource(UploadSourceRequest uploadSourceRequest)
            throws ParamException, UploadedFileException, BackEndException, IOException {

        log.debug("上传图片request：" + JSON.toJSONString(uploadSourceRequest));

        //检查请求参数
        validateRequestParam(uploadSourceRequest);

        // 传输上传的文件 以username.jpg命名
        String username = uploadSourceRequest.getUsername();
        LogHelper.setLogUserId(username);

        String type = uploadSourceRequest.getType();
        String ext = getFileFormatValid(uploadSourceRequest.getFile());
        if ("1".equals(type) && "jpg".equals(ext)) { //上传图片  删除旧图片，保存新图片
            String jpgTemp =  username + "_temp.jpg";
            String jpgOld = username + ".jpg";
            File uploadedFile = new File(uploadImgPath, jpgTemp);
            processUploadFile(uploadImgPath, uploadedFile, uploadSourceRequest.getFile());

            deleteOldFileAndRenameTempFile(uploadImgPath, jpgOld, jpgTemp);
        } else {
            throw new ParamException(uploadSourceRequest.toString());
        }

        UploadSourceResponse uploadSourceResponse = new UploadSourceResponse();
        uploadSourceResponse.setCode("0");

        log.debug("上传图片response：" + JSON.toJSONString(uploadSourceResponse));
        return JSON.toJSONString(uploadSourceResponse);
    }

    //删除旧文件 把临时文件重命名
    private void deleteOldFileAndRenameTempFile(String path, String oldFile, String tempFile) {
        File oldF = new File(path, oldFile);
        if(oldF.isFile() && oldF.exists()) {
            oldF.delete();
        }

        File tempF = new File(path, tempFile);
        tempF.renameTo(oldF);
    }

    private void processUploadFile(String uploadPath, File uploadedFile, MultipartFile file) throws IOException {
        makeUploadingFileDir(uploadPath);
        org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), uploadedFile);
    }

    private void makeUploadingFileDir(String uploadingPath) throws IOException {
        File file = new File(uploadingPath);
        if (!file.isDirectory()) {
            FileUtils.forceMkdir(file);
        }
    }

    private void validateRequestParam(UploadSourceRequest uploadSourceRequest) throws ParamException {
        if (ParamBeanValidator.isNotValid(uploadSourceRequest)) {
            throw new ParamException(uploadSourceRequest.toString());
        }
    }

    private String getFileFormatValid(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        log.debug("要上传的文件名：" + fileName);

        String ext = FilenameUtils.getExtension(fileName);
        ext = ext == null ? "" : ext.toLowerCase();

        return ext;
    }

}
