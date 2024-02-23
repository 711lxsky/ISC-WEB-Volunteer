package com.isc.backstage.service.impl;

import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.OnlineUserInformation;
import com.isc.backstage.domain.Result;
import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.entity.Account;
import com.isc.backstage.entity.User;
import com.isc.backstage.service.AccountService;
import com.isc.backstage.service.LoginService;
import com.isc.backstage.service.UserService;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import com.isc.backstage.utils.EnumDateUtil;
import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.utils.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-19
 */
@Service
@Log4j2
public class LoginServiceImpl implements LoginService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserService userService;

    @Resource
    private AccountService accountService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private EnumDateUtil enumDateUtil;

    @Resource
    private SecurityUtil securityUtil;

    @Override
    public Result<?> login(LoginUserDTO dto) throws DataErrorException, ServeErrorException, AuthenticationException,
            com.isc.backstage.Exception.AuthenticationException {
        if(Objects.nonNull(dto) && StringUtils.hasText(dto.getLoginAccountType())
                && StringUtils.hasText(dto.getLoginAccount()) && StringUtils.hasText(dto.getPassword())){
            Integer accountCategory = enumDateUtil.swapAccountTypeFromStringToInteger(dto.getLoginAccountType());
            log.info("category: {}", accountCategory);
            // 先看能不能根据账号信息把用户查出来，账户业务层返回用户id
            Account accountInfo =  accountService.getAccountInfoByAccountAndType(accountCategory, dto.getLoginAccount());
            if(Objects.isNull(accountInfo)){
                throw new DataErrorException(ExceptionConstant.NotFound.getMessage_EN());
            }
            Long userId = accountInfo.getUserId();
            User loginUserInfo = userService.getUserInfoById(userId);
            if(Objects.isNull(loginUserInfo)){
                throw new DataErrorException(ExceptionConstant.UserNotFound.getMessage_EN());
            }
            String rowPwdSalt = securityUtil.haveSimpleAESDecrypt(loginUserInfo.getSalt());
            String realPwd = securityUtil.addSaltIntoPassword(rowPwdSalt, dto.getPassword());
            // 这里将userId字符化是为了使用这唯一数据，所以实际上loadUserByUsername用的是id
            // 这里校验会出AuthenticationException
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(String.valueOf(userId), realPwd)
            );
            UserVO userVO = parseUserToUserVO(loginUserInfo);
            if(Objects.isNull(userVO.getId()) || !StringUtils.hasText(userVO.getName())
//                || !StringUtils.hasText(userVO.getHeadImgUrl())  这里引入OSS再加回来
                    || !StringUtils.hasText(userVO.getMobile())){
                throw new DataErrorException(ExceptionConstant.DateNull.getMessage_EN());
            }

            TokenVO tokenVO = jwtUtil.generateTokenVOWithUserInfo(userVO);
            log.info(userVO.toString());
            log.info(tokenVO.toString());
            // redis中只放AccessToken,因为后续重新登录需要把之前的token去掉，所以必须有一个AccessToken唯一标识
            jwtUtil.saveAccessTokenWithRefreshToken(tokenVO.getRefreshToken(), tokenVO.getAccessToken());
            return Result.success(
                    CodeAndMessage.SUCCESS.getCode(),
                    CodeAndMessage.SUCCESS.getDescription(),
                    new OnlineUserInformation(userVO, tokenVO)
            );
        }
        throw new DataErrorException(ExceptionConstant.DateNull.getMessage_EN());
    }

    private UserVO parseUserToUserVO(User user){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
