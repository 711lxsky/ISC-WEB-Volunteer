package com.isc.backstage.domain.VO;

/*
  @Author: 711lxsky
 * @Date: 2023-12-16
 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenVO {

    private String AccessToken;

    private String RefreshToken;
}
