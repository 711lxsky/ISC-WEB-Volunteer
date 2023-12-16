package com.isc.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Test
    void contextLoads() {
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

}
