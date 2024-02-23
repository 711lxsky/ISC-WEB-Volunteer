package com.isc.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backstage.domain.Result;
import com.isc.backstage.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

/**
* @author zyy
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-12-16 16:43:57
*/
public interface UserService extends IService<User> {

    User getUserInfoById(Long userId);

    User getUserByUsername(String username);

    Result<?> updateUserAvatar(MultipartFile newAvatarFile);

    void saveUserDetailsIntoRedisWithUserId(String userId);

    UserDetails getUserDetailsFromRedisWithUserId(String userId);
}
