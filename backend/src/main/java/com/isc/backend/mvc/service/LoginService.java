package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.User;
import com.isc.backend.setting.Result;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    Result<?> login(User user);

    Result<?> logout();
}
