package com.isc.backstage.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */

public class JwtUtil {

    public String generateTokenByHMAC(Object data){
        // 创建JWS头，设置签名算法以及类型
        JWSHeader jwsHeader = new JWSHeader
                .Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(data);
    }
}
