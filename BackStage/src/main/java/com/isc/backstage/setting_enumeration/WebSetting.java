package com.isc.backstage.setting_enumeration;

import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-04
 */
public class WebSetting {

   @Getter
    private static final String A_C_A_O = "Access-Control-Allow-Origin";

   @Getter
    private static final String All = "*";

   @Getter
   private static final String MappingAll = "/**";

   @Getter
    private static final String C_C = "Cache-Control";

   @Getter
    private static final String N_C = "no-cache";

   @Getter
    private static final String ResponseContentType = "application/json";

   @Getter
    private static final String ResponseCharacterEncoding = "UTF-8";

   @Getter
    private static final String LogoutUrl = "/user/logout";

   @Getter
    private static final String[] AllowCordsMethods = {
           "GET",
           "HEAD",
           "POST",
           "PUT",
           "DELETE",
           "OPTIONS"
   };

   @Getter
    private static final Integer MaxAge = 3600;

   @Getter
    private static final String Https = "https://";

   @Getter
    private static final String NullString = "";

   @Getter
    private static final String ContentMD5 = "Content-MD5";
}
