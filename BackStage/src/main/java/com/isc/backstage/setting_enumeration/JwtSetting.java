package com.isc.backstage.setting_enumeration;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.Data;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-20
 */
@Data
public class JwtSetting {

    @Getter
    private static final String[] WhiteList = {
            // 测试放行
            "/test/register",
            "/test/login",
            "/test/refresh-access-token",

            // 白名单放行
            "/user/login",
            "/user/register",
            "/user/refresh"
    };

    @Getter
    public static final JWSAlgorithm HMAC = JWSAlgorithm.HS256;

    @Getter
    private static final String accessSecret = "711lxsky-isc-web-volunteer-" +
            "zyy-security-json-web-Token-access-token-secret";

    @Getter
    private static final String refreshSecret = "711lxsky-isc-web-volunteer-" +
            "zyy-security-json-web-Token-refresh-token-secret";

    @Getter
    private static final byte[] accessSecretBytes = accessSecret.getBytes();

    @Getter
    private static final byte[] refreshSecretBytes = refreshSecret.getBytes();

    @Getter
    private static final String issuer = "zyy";

    @Getter
    private static final String audience = "isc";

    @Getter
    private static final Integer accessTokenExpireTimeForMinutes = 122;

    @Getter
    private static final Integer refreshTokenExpireTimeForHours = 24 * 7 * 2;

    @Getter
    private static final String blackName = "Black-";

    @Getter
    private static final String authorization_header = "Authorization";

    @Getter
    private static final String accessTokenFormat = "user:%s-at:%s";

    @Getter
    private static final String blackListFormat = "black:%s";


}
