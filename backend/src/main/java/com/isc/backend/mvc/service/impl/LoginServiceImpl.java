package com.isc.backend.mvc.service.impl;

import com.isc.backend.Util.JwtTestUtil;
import com.isc.backend.Util.RedisCache;
import com.isc.backend.mvc.entity.LoginUser;
import com.isc.backend.mvc.entity.User;
import com.isc.backend.mvc.service.LoginService;
import com.isc.backend.setting.RCodeMessage;
import com.isc.backend.setting.Result;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxsky711
 * @date 2023-12-15 10:39
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    @Override
    public Result<?> login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 未通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        // 认证通过，使用userid生成jwt并存入Result返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtTestUtil.createJWT(userid);
        Map<String, String> record = new HashMap<>();
        record.put("token", jwt);
        // 完整信息放入redis, userid为key
        redisCache.setCacheObject("login:"+userid, loginUser);
        return new Result<>(RCodeMessage.LoginSuccess.getCode(),
                RCodeMessage.LoginSuccess.getDescription(),
                record);
    }

    public Result<?> logout(){
        // 获取SecurityContextHolder中用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        // 删除redis中的值
        redisCache.deleteObject("login:" + userid);
        return new Result<>(RCodeMessage.LogoutSuccess.getCode(),
                RCodeMessage.LogoutSuccess.getDescription(),
                true);
    }
}
