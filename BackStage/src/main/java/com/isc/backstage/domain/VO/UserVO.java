package com.isc.backstage.domain.VO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Data
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String headImgUrl;

    private String mobile;

}
