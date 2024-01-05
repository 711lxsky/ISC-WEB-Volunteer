package com.isc.backstage.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@AllArgsConstructor
@Data
public class TokenDTO {

    private String AccessTokenId;

    private String AccessToken;

    private String RefreshToken;
}
