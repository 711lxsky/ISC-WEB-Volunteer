package com.isc.backend;

import com.isc.backend.mvc.entity.User;
import com.isc.backend.mvc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lxsky711
 * @date 2023-12-14 21:23
 */
@SpringBootTest
public class MapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
