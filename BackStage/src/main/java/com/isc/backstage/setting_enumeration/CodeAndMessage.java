package com.isc.backstage.setting_enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Getter
@AllArgsConstructor
public enum CodeAndMessage {


    SUCCESS(200, "OK", "成功"),
    CREATED(1, "Created", "创建成功"),
    UPDATED(2, "Updated", "更新成功"),
    DELETED(3, "Deleted", "删除成功"),

    INTERNAL_SERVER_ERROR(9999, "Internal Server Error", "服务器未知错误"),

    UN_AUTHENTICATION(401, "Authentication Failed", "认证失败"),
    CANT_SIGN_OR_ENCRYPT(10011, "Can't sign or encrypt for the jwt", "无法对JWT签名或加密"),
    UN_AUTHORIZATION(10010, "Authorization Failed", "授权失败"),
    NOT_FOUND_TOKEN(10021, "Not Found TOKEN", "未携带令牌"),
    TOKEN_INVALID(10040, "Token Invalid", "令牌失效"),
    TOKEN_EXPIRED(10050, "Token Expired", "令牌过期"),
    ACCESS_TOKEN_EXPIRED(10051, "Access Token Expired", "访问令牌过期"),
    REFRESH_TOKEN_EXPIRED(10052, "Refresh Token Expired", "刷新令牌过期"),
    KEY_LENGTH_ERROR(10052, "Token key has some error", "Token密匙长度有问题"),

    CANT_JSON_PARSE(11, "Can't parse JSON", "无法转换JSON数据"),
    OBJECT_IS_NULL(12, "This Object is null", "此对象为空"),

    NOT_FOUND(10020, "Not Found", "资源不存在"),
    CANT_PARSE(10023, "Can' t parse this data", "无法解析数据"),
    DATA_ERROR(10025, "Data has some error", "数据错误"),
    PARAMETER_ERROR(10030, "Parameters Error", "参数错误"),
    DUPLICATED(10060, "Duplicated", "字段重复"),
    DUPLICATED_DATABASE(10061, "Duplicated Database", "数据库中已存在该记录"),
    FORBIDDEN(10070, "Forbidden", "禁止操作"),
    METHOD_NOT_ALLOWED(10080, "Method Not Allowed", "请求方法不允许"),
    REFRESH_FAILED(10100, "Get Refresh Token Failed", "刷新令牌获取失败"),
    FILE_TOO_LARGE(10110, "File Too Large", "文件体积过大"),
    FILE_TOO_MANY(10120, "File Too Many", "文件数量过多"),
    FILE_EXTENSION(10130, "File Extension Not Allowed", "文件扩展名不符合规范"),
    REQUEST_LIMIT(10140, "Too Many Requests", "请求过于频繁，请稍后重试"),
    FAIL(10200, "Failed", "失败");

    /**
     * 消息码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 中文描述
     */
    private final String zhDescription;
}
