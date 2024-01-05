package com.isc.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.entity.User;
import com.isc.backstage.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Log4j2
@SpringBootTest
class BackStageApplicationTests {

    @Resource
    private UserMapper mapper;

    @Resource
    private JwtUtil jwtUtil;

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

}
