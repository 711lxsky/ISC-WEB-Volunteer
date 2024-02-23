package com.isc.backstage.setting_enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-25
 */
@AllArgsConstructor
@Getter
public enum ExceptionConstant {

    UserRepeat("This User information is repeated", "用户信息重复"),
    PasswordDifferent("The first password is different from the second password", "前后密码不一致"),
    TelephoneNumberInvalid("The telephone-number is invalid", "电话号码无效"),
    DateNull("Some date is null", "存在空数据"),
    NotFound("Unable to find data that meets the criteria", "无法找到满足条件的数据"),
    UserNotFound("Can not find the user information", "无法找到目标用户信息"),
    EnumDateError("There are some enum class date has error", "枚举类型数据有误"),
    RefreshTokenVerifyError("There are some error during verify the refresh-token", "校验RefreshToken错误"),
    AccessTokenVerifyError("There are some error during verify the access-token", "校验AccessToken错误"),
    EncryptError(". Unable to encrypt", "无法进行加密"),
    DecryptError(". Unable to decrypt", "无法进行解密"),
    FileStreamError("There are some error for the file stream", "文件流错误"),
    ThreadInterruptError("There has some error happened during the thread interrupt", "线程阻塞过程中发生错误"),
    FileNameError("The file's name may have some error", "获取文件名错误"),
    AnalyzeFileTypeError("There are some error during analyzing the file type", "解析文件类型出错"),
    FileTypeUnsupported("This file type is unsupported", "此文件类型不支持"),
    OSSBuildError("OSS service has some error during building", "新建OSS服务出现问题"),
    OSSSaveFileError("There are some error during OSS save file", "OSS服务存储错误"),
    CannotSaveDateIntoRedis("Can't save any date into redis", "无法保存数据至Redis"),
    CannotGetDateFromRedis("Can't get any date from redis", "无法从Redis中读取数据"),
    RedisDateError("There has some error exist the Redis service", "Redis服务数据错误"),
    FilePartUploadError("There are some error during upload file to oss with part approach", "分片上传文件至OSS错误");

    private final String Message_EN;
    private final String MMessage_ZH;
}
