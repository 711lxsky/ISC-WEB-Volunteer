package com.isc.backstage.setting_enumeration;

import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2024-02-22
 */

public class FileSetting {

    @Getter
    private static final Integer AvatarFileType = 1;

    @Getter
    private static final String [] ImgFileTypes = {"jpg", "jpeg", "png", "gif", "bmp", "svg"};

    @Getter
    private static final String UserAvatarFormat = "u_%s-av_%s";

}
