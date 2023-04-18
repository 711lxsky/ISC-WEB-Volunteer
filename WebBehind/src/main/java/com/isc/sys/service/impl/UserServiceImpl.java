package com.isc.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.sys.entity.User;
import com.isc.sys.mapper.UserMapper;
import com.isc.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名和密码进行查询，结果不为空则生成token，并将用户信息存入redis
         LambdaQueryWrapper<User>  wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(User::getUsername,user.getUsername());
         wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if(loginUser != null){
         // 生成token，暂时用UUID，最终方案应是jwt
            String key = "user:" +  UUID.randomUUID();

        // 存入redis
        loginUser.setPassword(null);
        redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

        // 返回数据
        Map<String,Object> data = new HashMap<>();
        data.put("token",key);
        return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String,Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            // 角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);

            return data;
        }
        return null;
    }
}