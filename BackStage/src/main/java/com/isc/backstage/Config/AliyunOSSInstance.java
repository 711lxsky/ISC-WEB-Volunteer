package com.isc.backstage.Config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import com.isc.backstage.setting_enumeration.WebSetting;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 711lxsky
 * @Date: 2024-02-21
 */

@Getter
@Configuration
public class AliyunOSSInstance {

    @Value("${aliyunoss.endpoint}")
    private String endpoint;

    @Value("${aliyunoss.bucket}")
    private String bucket;

    @Value("${aliyunoss.avatarPath}")
    private String avatarPath;

    @Value("${aliyunoss.partSize}")
    private long partSize;

    @Value("${aliyunoss.connectionTimeout}")
    private  int connectionTimeout;

    @Value("${aliyunoss.requestTimeout}")
    private final int requestTimeout = 1000;

    @Value("${aliyunoss.socketTimeout}")
    private final int socketTimeout = 1000;

    @Bean
    public OSS getOSSClient() {
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
            conf.setConnectionTimeout(connectionTimeout);
            conf.setRequestTimeout(requestTimeout);
            conf.setSocketTimeout(socketTimeout);
            return new OSSClientBuilder().build(WebSetting.getHttps()+endpoint, credentialsProvider, conf);
        } catch (ClientException e) {
            throw new ServeErrorException(e.getMessage()+"  "+ ExceptionConstant.OSSBuildError.getMessage_EN());
        }
    }

}
