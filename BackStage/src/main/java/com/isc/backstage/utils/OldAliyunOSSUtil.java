package com.isc.backstage.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.isc.backstage.Config.AliyunOSSInstance;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.setting_enumeration.FileSetting;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import com.isc.backstage.setting_enumeration.StringConstant;
import com.isc.backstage.setting_enumeration.WebSetting;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @Author: 711lxsky
 * @Date: 2024-02-03
 */
@Component
@Log4j2
public class OldAliyunOSSUtil {

    /**
     * 这里刚开始使用的是流式上传，出现bug(可以正常上传文件但是文件元数据损坏)
     * 后来暂时弃用

     * 实际上bug也是 文件类型检查进程影响流式上传
     * 已经修复，可以正常使用

     * 可以考虑针对文件大小使用 一般流式上传 或者 分片上传
     */

    @Resource
    private AliyunOSSInstance aliyunOSSInstance;


    private OSS getOssClient() throws ServeErrorException{
        String endpointForHttpsForm =
                WebSetting.getHttps() + aliyunOSSInstance.getEndpoint();
//        String endpoint = FileSetting.getEndpoint();
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            return new OSSClientBuilder().build(endpointForHttpsForm, credentialsProvider);
        } catch (ClientException e) {
            throw new ServeErrorException(e.getMessage()+"  "+ExceptionConstant.OSSBuildError.getMessage_EN());
        }
    }

    private String getFileOriginalName(MultipartFile file) throws DataErrorException{
        String fileName = file.getOriginalFilename();
        if(StringUtils.hasText(fileName)){
            return fileName;
        }
        throw new DataErrorException(ExceptionConstant.FileNameError.getMessage_EN());
    }

    private String saveDateToOSS(String fileFullName, InputStream fileInputStream) throws ServeErrorException{
        // 此函数大体参照阿里官方文档书写
        OSS oss = this.getOssClient();
        try {
            PutObjectRequest request = new PutObjectRequest(aliyunOSSInstance.getBucket(), fileFullName, fileInputStream);
            PutObjectResult result = oss.putObject(request);
            if(Objects.nonNull(result)){
                log.info("result: {}",result);
                log.info("requestId: {}", result.getRequestId());
                return getFullAvatarURL(fileFullName);
            }
            throw new ServeErrorException(ExceptionConstant.OSSSaveFileError.getMessage_EN());
        }catch (OSSException | com.aliyun.oss.ClientException e){
            throw new ServeErrorException(e.getMessage()+"  "+ExceptionConstant.OSSSaveFileError.getMessage_EN());
        }finally {
            oss.shutdown();
        }
    }

    public String uploadUserAvatar(String userInfo, MultipartFile file) throws DataErrorException, ServeErrorException{
        try {
            // 获取数据流
            InputStream checkStream = file.getInputStream();
            // 获取文件原始名
            String fileOriginName = this.getFileOriginalName(file);
            // 借助hutool工具包获取文件类型， 第三个参数为枚举类型，用以比对目标文件后缀
            String fileType = this.getFileType(checkStream, fileOriginName, FileSetting.getImgFileTypes());
            // 设置新文件名
            String fileFullName = aliyunOSSInstance.getAvatarPath()
                    + this.getAvatarNewName(userInfo, IdUtil.simpleUUID())
                    + StringConstant.DOT
                    + fileType;
            InputStream uploadStream = file.getInputStream();
            return this.saveDateToOSS(fileFullName, uploadStream);
        }catch (IOException e){
            throw new DataErrorException(
                    e.getMessage()
                            + "  "
                            + ExceptionConstant.FileStreamError.getMessage_EN());
        }
    }

    private String getFileType(InputStream inputStream, String fileOriginName, String[] needFileTypes) throws DataErrorException{
        String fileType = FileTypeUtil.getType(inputStream, fileOriginName);
        if(! StringUtils.hasText(fileType)){
            throw new DataErrorException(ExceptionConstant.AnalyzeFileTypeError.getMessage_EN());
        }
        for(String fileTypeNeed : needFileTypes){
            if(Objects.equals(fileTypeNeed,fileType)){
                return fileType;
            }
        }
        throw new DataErrorException(ExceptionConstant.FileTypeUnsupported.getMessage_EN());
    }

    private String getFullAvatarURL(String fileName){
        return WebSetting.getHttps()
                + aliyunOSSInstance.getBucket()
                + StringConstant.DOT
                + aliyunOSSInstance.getEndpoint()
                + StringConstant.SEPARATOR
                + fileName;
    }

    private String getAvatarNewName(String userInfo, String fileName){
        return String.format(FileSetting.getUserAvatarFormat(), userInfo, fileName);
    }


}
