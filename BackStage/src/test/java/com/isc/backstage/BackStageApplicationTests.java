package com.isc.backstage;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.backstage.Config.AliyunOSSInstance;
import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.entity.User;
import com.isc.backstage.mapper.UserMapper;
import com.isc.backstage.utils.EnumDateUtil;
import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.utils.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Log4j2
@SpringBootTest
class BackStageApplicationTests {

    @Resource
    private UserMapper mapper;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private EnumDateUtil enumDateUtil;

    @Resource
    private SecurityUtil securityUtil;

    @Test
    void contextLoads() {
        System.out.println(String.format("----- user-at:%s -----=%S=", "test+test+test", "ddd"));
    }

    @Test
    public void getUserByUsername(){
        String username = "test1";
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        Optional<User> optionalUser = Optional.ofNullable(mapper.selectOne(wrapper));
        log.info(optionalUser.orElseThrow(() -> new UsernameNotFoundException("抱歉，用户不存在")));
        UserVO testVO = new UserVO();
        User user = new User(12L, 0, "test", null, "114514", "dfsf", "sdfdf", null, null, null, null, 0);
        BeanUtils.copyProperties(user, testVO);
        log.info(testVO);

    }

    @Test
    public void testPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("711zyy");
        log.info("加密之后：{}", password);
        log.info("使用原密码与其匹配：{}", encoder.matches("711zyy", password));
    }

    @Test
    public void testException(){
        ServeErrorException e1 = new ServeErrorException(1, "serveError");
        log.info(e1.toString()+ e1.getCode()+e1.getMessage()+e1.getHttpStatusCode());
        AuthenticationException a1 = new AuthenticationException(2, "authentication");
        log.info(a1.toString() + a1.getCode() + a1.getMessage()+a1.getHttpStatusCode());
    }

    @Test
    public void testUrlEqual(){
        String url1 = "/webjars/**";
        String url2 = "/webjars/js/app.b0c0d7df.js";
        log.info(url2.equals(url1));
    }

    @Test
    public void testCustomSecurity(){
        String key = "1234567887654321";
        byte [] keyByte = key.getBytes(StandardCharsets.UTF_8);
        AES aes1 =  SecureUtil.aes(keyByte);
        AES aes2 = SecureUtil.aes();
        AES aes3 = SecureUtil.aes(keyByte);
        log.info(aes1);
        log.info(aes2);
        String test1 = "test for ase";
        byte [] en_test1 = aes1.encrypt(test1);
        log.info(en_test1);
        String de_test1 = aes3.decryptStr(en_test1);
        log.info(de_test1);
    }

    @Test
    public void anyTest1(){
        log.info(enumDateUtil.swapAccountTypeFromStringToInteger("TelephoneNumber"));
        String test = securityUtil.haveSimpleAESEncrypt("asimplete78778st*@1");
        log.info(test);
        log.info(securityUtil.haveSimpleAESDecrypt(test));
        log.info(securityUtil.haveSimpleAESDecrypt("sL2gtYDBRUWc5AF1OfL6xg=="));
    }

    @Test
    public void testSecure1(){
        String salt = "0123456";
        String password = "abcd";
        int lenS = salt.length(), lenP = password.length();
        int sumLen = lenS + lenP;
        char [] newStr = new char[sumLen];
        for(int index = 0, ptrS = 0, ptrP = 0; index < sumLen; ){
            if(ptrS < lenS){
                newStr[index++] =  salt.charAt(ptrS++);
            }
            if(ptrP < lenP){
                newStr[index++] =  password.charAt(ptrP++);
            }
        }
        String s = new String(newStr);
        System.out.println(s);
        log.info(s);

        Long id = 8989L;
        log.info(String.valueOf(id));
        log.info(id.toString());
    }

    @Test
    public void testJwt(){
        UserVO testVO1 = new UserVO(4L, "register-test3", null, "13451411453");
        TokenVO testTokenVO = jwtUtil.generateTokenVOWithUserInfo(testVO1);
        log.info(testTokenVO);
        jwtUtil.saveAccessTokenWithRefreshToken(testTokenVO.getRefreshToken(), testTokenVO.getAccessToken());
    }

    @Resource
    private AliyunOSSInstance aliyunOSSInstance;
    @Test
    public void testAutoWired(){
        log.info(aliyunOSSInstance.getEndpoint());
    }


}
