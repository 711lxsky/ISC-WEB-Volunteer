package com.isc.backend.Util;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    @Value("${token-name}")
    private String tokenName;
    private static final long JWT_EXPIRE = 30*60*1000L;

    private static final String JWT_KEY = "iscWeb";

    private static final String JWT_ISS = "zyy";

    private static final String JWT_AUD = "frontend";

    public String getTokenName() {
        return tokenName;
    }

    public String createToken(Object data){
        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime+JWT_EXPIRE;
        JwtBuilder builder = Jwts.builder()
                .setExpiration(new Date(expireTime))
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS256,encodeSecret(JWT_KEY))
                .setIssuer(JWT_ISS)
                .setSubject(JSON.toJSONString(data))
                .setId(UUID.randomUUID()+"")
                .setAudience(JWT_AUD);
        log.info(data.toString());
        /* 这里jdk版本为1.8!!!*/
        return builder.compact();
    }

    public SecretKey encodeSecret(String key){
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        return new SecretKeySpec(encode,0,encode.length,"AES");
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T parseToken(String token,Class<T> tClass){
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return JSON.parseObject(body.getSubject(),tClass);
    }

}
