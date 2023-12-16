package com.isc.backstage.domain;

import lombok.*;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result <T> {

    private Integer code;

    private String message;

    private T data;
}
