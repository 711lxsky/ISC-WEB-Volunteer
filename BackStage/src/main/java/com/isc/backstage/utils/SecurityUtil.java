package com.isc.backstage.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-28
 */
@Component
public class SecurityUtil {

    private static final String Key_Str = "!g@a#v$e%a^k&e*y";

    private static final byte [] Key_Byte = Key_Str.getBytes(StandardCharsets.UTF_8);

    private static final String Iv_Str = "2010807050406030";

    private static final byte [] Iv_Byte = Iv_Str.getBytes(StandardCharsets.UTF_8);

    private static final AES aSimpleAES = new AES(
            Mode.CBC,
            Padding.PKCS5Padding,
            Key_Byte,
            Iv_Byte
    );

    public String haveSimpleAESEncrypt(String rawStr) throws DataErrorException{
        try {
            return aSimpleAES.encryptBase64(rawStr);
        }catch (RuntimeException e){
            throw new DataErrorException(e.getMessage() + ExceptionConstant.EncryptError.getMessage_EN());
        }

    }

    public String haveSimpleAESDecrypt(String encryptStr){
        try {
            return aSimpleAES.decryptStr(encryptStr);
        }catch (RuntimeException e){
            throw new DataErrorException(e.getMessage() + ExceptionConstant.DecryptError.getMessage_EN());
        }

    }

    public String addSaltIntoPassword(String salt, String password) throws DataErrorException{
        if( StringUtils.hasText(salt) && StringUtils.hasText(password)){
            int lenS = salt.length(), lenP = password.length();
            int sumLen = lenS + lenP;
            char [] desStr = new char[sumLen];
            for(int index = 0, ptrS = 0, ptrP = 0; index < sumLen; ){
                if(ptrS < lenS){
                    desStr[index++] = salt.charAt(ptrS++);
                }
                if(ptrP < lenP){
                    desStr[index++] = password.charAt(ptrP++);
                }
            }
            return new String(desStr);
        }
        throw new DataErrorException(ExceptionConstant.DateNull.getMessage_EN());

    }
}
