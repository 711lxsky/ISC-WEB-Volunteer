package com.isc.backstage.utils;

import cn.hutool.core.util.StrUtil;
import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.DTO.TokenDTO;
import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import com.isc.backstage.setting_enumeration.JwtSetting;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@Log4j2
@Component
public class JwtUtil {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JacksonUtil jacksonUtil;

    private String getRealUserJwtIdForAccessToken(String userid, String jwtId){
        return String.format(JwtSetting.getAccessTokenFormat(), userid, jwtId);
    }

    private String getRealBlackJwtName(String accessTokenId){
        return String.format(JwtSetting.getBlackListFormat(), accessTokenId);
    }

    public TokenVO generateTokenVOWithUserInfo(UserVO userVO) throws ServeErrorException, AuthenticationException{
        String userId = String.valueOf(userVO.getId());
        String jsonUserInfo = jacksonUtil.objectToJson(userVO);
        String accessToken = generateAccessTokenByHMACWithData(jsonUserInfo);
        String refreshToken = generateRefreshTokenByHMAC(getRealUserJwtIdForAccessToken(userId, getJwtIdFromAccessToken(accessToken)));
        return new TokenVO(accessToken, refreshToken);
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Param: 需要放到AccessToken中的对象
     * @Return: AccessToken和RefreshToken所在的数据类
     * @Description: 自顶向下调用方法生成TokenId、AccessToken和RefreshToken并返回
     */
    private TokenDTO generateTokenDTO(Object data) throws ServeErrorException{
        String uuid = UUID.randomUUID().toString();
        String jsonData = jacksonUtil.objectToJson(data);
        return new TokenDTO(uuid, generateAccessTokenByHMACAndJwtId(jsonData, uuid) ,generateRefreshTokenByHMAC(uuid));
    }

    private TokenVO transformTokenDTOToVO(TokenDTO dto){
        return new TokenVO(dto.getAccessToken(), dto.getRefreshToken());
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Param: AccessToken所的jwtId
     * @Description: 生成AccessToken的伴随RefreshToken
     */
    private String generateRefreshTokenByHMAC(String jwtId){
        JWSHeader jwtHMACHeader = generateJWTHeader(JwtSetting.getHMAC());
        SignedJWT refreshToken = new SignedJWT(jwtHMACHeader, buildRefreshTokenClaimSet(jwtId));
        try {
            refreshToken.sign(generateHMACRefreshJWSigner());
            return refreshToken.serialize();
        } catch (JOSEException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getCode(), CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Param: 需要放在AccessToken中的数据对象和指定的jwtId
     * @Description: 生成AccessToken并返回
     */
    private String generateAccessTokenByHMACAndJwtId(String data, String uuid){
        SignedJWT accessToken = new SignedJWT(generateJWTHeader(JwtSetting.getHMAC()), buildAccessTokenClaimsSetWithJwtId(data, uuid));
        return signAccessToken(accessToken);
    }

    private String generateAccessTokenByHMACWithData(String data){
        SignedJWT accessToken = new SignedJWT(generateJWTHeader(JwtSetting.getHMAC()), buildAccessTokenClaimsSet(data));
        return signAccessToken(accessToken);
    }


    private String signAccessToken(SignedJWT accessToken){
        try {
//            Payload payload = new Payload(jsonData);
            accessToken.sign(generateHMACAccessJWSigner());
            return accessToken.serialize();
        } catch (JOSEException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getCode(), CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    private String generateRefreshAccessToken(JWTClaimsSet claimsSet){
        SignedJWT newAccessToken = new SignedJWT(generateJWTHeader(JwtSetting.getHMAC()), claimsSet);
        try {
            newAccessToken.sign(generateHMACAccessJWSigner());
            return newAccessToken.serialize();
        } catch (JOSEException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getCode(), CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    public  <T>T parseAccessTokenToClass(String token, Class<T> tClass)throws ServeErrorException, AuthenticationException{
        JWTClaimsSet jwtClaimsSet = verifyAccessToken(token);
        log.info("过期时间： {}",jwtClaimsSet.getExpirationTime());
        log.info("jti: {}", jwtClaimsSet.getJWTID());
        String jsonObject = jwtClaimsSet.getSubject();
        return jacksonUtil.jsonToObject(jsonObject, tClass);
    }

    private SignedJWT getSignedJWTFromToken(String token) throws ServeErrorException{
        try {
            return SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_PARSE.getDescription());
        }
    }

    private JWTClaimsSet verifyRefreshToken(String refreshToken) throws AuthenticationException, ServeErrorException{
        SignedJWT parse = getSignedJWTFromToken(refreshToken);
        try {
            if(! parse.verify(generateHMACRereshJWSVerifier())){
                throw new AuthenticationException(ExceptionConstant.RefreshTokenVerifyError.getMessage_EN());
            }
            return verifyTokenForExpired(parse);
        } catch (JOSEException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    private JWTClaimsSet verifyAccessToken(String accessToken) throws AuthenticationException, ServeErrorException{
        SignedJWT parse = getSignedJWTFromToken(accessToken);
        try {
            if(! parse.verify(generateHMACAccessJWSVerifier())){
                throw new AuthenticationException(ExceptionConstant.AccessTokenVerifyError.getMessage_EN());
            }
            return verifyTokenForExpired(parse);
        }catch (JOSEException e){
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    private JWTClaimsSet verifyTokenForExpired(SignedJWT parse) throws AuthenticationException, ServeErrorException{
        try {
            JWTClaimsSet jwtClaimsSet = parse.getJWTClaimsSet();
            if(Calendar.getInstance().getTime().after(jwtClaimsSet.getExpirationTime())){
                throw new AuthenticationException(CodeAndMessage.TOKEN_EXPIRED.getDescription());
            }
            return jwtClaimsSet;
        } catch (ParseException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_PARSE.getDescription());
        }
    }



    public String getJwtIdFromAccessToken(String accessToken) throws ServeErrorException, AuthenticationException{
        JWTClaimsSet jwtClaimsSetForAccessToken = verifyAccessToken(accessToken);
        return jwtClaimsSetForAccessToken.getJWTID();
    }

    public String getJwtIdFromRefreshToken(String refreshToken) throws ServeErrorException, AuthenticationException{
        JWTClaimsSet jwtClaimsSetForRefreshToken = verifyRefreshToken(refreshToken);
        return jwtClaimsSetForRefreshToken.getJWTID();
    }

    private String getSubjectFromAccessToken(String accessToken)throws ServeErrorException, AuthenticationException{
        JWTClaimsSet jwtClaimsSetForAccessToken = verifyAccessToken(accessToken);
        return jwtClaimsSetForAccessToken.getSubject();
    }

    private String getSubjectFromRefreshToken(String refreshToken) throws ServeErrorException, AuthenticationException{
        JWTClaimsSet jwtClaimsSetForRefreshToken = verifyRefreshToken(refreshToken);
        return jwtClaimsSetForRefreshToken.getSubject();
    }

    private String getUseridFormAccessToken(String accessToken) throws AuthenticationException{
        //JWTClaimsSet claimsSet = verifyToken(accessToken);
        UserVO userFromAccessToken = parseAccessTokenToClass(accessToken, UserVO.class);
        if(Objects.isNull(userFromAccessToken)){
            throw new AuthenticationException(CodeAndMessage.TOKEN_INVALID.getDescription());
        }
        return userFromAccessToken.getId().toString();
    }

    /**
     * @Author: 711lxsky
     * @Param: AccessToken的唯一标识
     * @Return: 新生成的AccessToken
     * @Description: 刷新AccessToken并返回新的AccessToken
     */
    private String refreshAccessTokenByJwtId(String accessTokenId) throws AuthenticationException, ServeErrorException{
        String originAccessToken = redisUtil.getForStrKey(accessTokenId);
        if(StrUtil.isBlank(originAccessToken)){
            throw new AuthenticationException(CodeAndMessage.TOKEN_EXPIRED.getDescription());
        }
        JWTClaimsSet oldClaimsSet = verifyAccessToken(originAccessToken);
        JWTClaimsSet newClaimsSet = buildAccessTokenClaimsSetWithJwtId(oldClaimsSet.getSubject(), accessTokenId);
        return generateRefreshAccessToken(newClaimsSet);
    }

    public Boolean putAccessTokenIntoBlackList(String accessToken) throws ServeErrorException, AuthenticationException{
        String jwtId = getJwtIdFromAccessToken(accessToken);
        redisUtil.set(JwtSetting.getBlackName()+jwtId, accessToken);
        return redisUtil.remove(jwtId);
    }

    public Boolean judgeAccessTokenInBlackList(String accessTokenId){
        return redisUtil.exists(getRealBlackJwtName(accessTokenId));
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Param: 加密算法
     * @Return: JWS头
     * @Description: 创建JWS头，设置签名算法以及类型
     */
    private JWSHeader generateJWTHeader(JWSAlgorithm algorithm){
        return new JWSHeader
                .Builder(algorithm)
                .type(JOSEObjectType.JWT)
                .build();
    }

    private Date getSignTime(){
        return Calendar.getInstance().getTime();
    }

    private Date getAccessTokenExpireTime(){
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, JwtSetting.getAccessTokenExpireTimeForMinutes());
        return currentTime.getTime();
    }

    private Date getRefreshTokenExpireTime(){
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.HOUR, JwtSetting.getRefreshTokenExpireTimeForHours());
        return currentTime.getTime();
    }

    private JWTClaimsSet buildAccessTokenClaimsSetWithJwtId(String data, String jwtId){
        return new JWTClaimsSet.Builder()
                .issuer(JwtSetting.getIssuer())
                .subject(data)
                .audience(JwtSetting.getAudience())
                .expirationTime(getAccessTokenExpireTime())
                .issueTime(getSignTime())
                .notBeforeTime(getSignTime())
                .jwtID(jwtId)
                .build();
    }

    private JWTClaimsSet buildAccessTokenClaimsSet(String data) {
        return new JWTClaimsSet.Builder()
                .issuer(JwtSetting.getIssuer())
                .subject(data)
                .audience(JwtSetting.getAudience())
                .expirationTime(getAccessTokenExpireTime())
                .issueTime(getSignTime())
                .notBeforeTime(getSignTime())
                .jwtID(UUID.randomUUID().toString())
                .build();
    }


    private JWTClaimsSet buildRefreshTokenClaimSet(String accessTokenId){
        return new JWTClaimsSet.Builder()
                .issuer(JwtSetting.getIssuer())
                .subject(accessTokenId)
                .audience(JwtSetting.getAudience())
                .expirationTime(getRefreshTokenExpireTime())
                .issueTime(getSignTime())
                .notBeforeTime(getSignTime())
                .jwtID(UUID.randomUUID().toString())
                .build();
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Return: AccessToken对应的JWS签名
     * @Description: 拿到AccessToken密匙后生成签名返回
     */
    private JWSSigner generateHMACAccessJWSigner() throws ServeErrorException{
        try {
            return new MACSigner(JwtSetting.getAccessSecret());
        } catch (KeyLengthException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.KEY_LENGTH_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Return: AccessToken对应的解释器
     * @Description: 拿到AccessToken密匙后生成解释器返回
     */
    private JWSVerifier generateHMACAccessJWSVerifier() throws ServeErrorException{
        try {
            return new MACVerifier(JwtSetting.getAccessSecret());
        } catch (JOSEException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getCode(), CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Return: RefreshToken对应的JWS签名
     * @Description: 拿到RefreshToken密匙后生成签名返回
     */
    private JWSSigner generateHMACRefreshJWSigner() throws ServeErrorException{
        try {
            return new MACSigner(JwtSetting.getRefreshSecret());
        } catch (KeyLengthException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.KEY_LENGTH_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * @Author: 711lxsky
     * @Date: 2023-12-20
     * @Return: RefreshToken对应的解释器
     * @Description: 拿到RefreshToken密匙后生成解释器返回
     */
    private JWSVerifier generateHMACRereshJWSVerifier() throws ServeErrorException{
        try {
            return new MACVerifier(JwtSetting.getRefreshSecret());
        } catch (JOSEException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getCode(), CodeAndMessage.CANT_SIGN_OR_ENCRYPT.getDescription());
        }
    }

    public void saveAccessTokenWithRefreshToken(String refreshToken, String accessToken)throws ServeErrorException, AuthenticationException{
        String accessTokenId = this.getSubjectFromRefreshToken(refreshToken);
        this.saveAccessToken(accessTokenId, accessToken);
    }

    private void saveAccessToken(String accessTokenId, String accessToken) throws ServeErrorException{
        if(StringUtils.hasText(accessTokenId) && StringUtils.hasText(accessToken)){
            redisUtil.set(accessTokenId, accessToken);
        }
        else {
            throw new ServeErrorException(CodeAndMessage.DATA_ERROR.getDescription());
        }
    }

    public Boolean judgeAccessTokenInRedis(String userid, String accessTokenId){
        String realJwtId = getRealUserJwtIdForAccessToken(userid, accessTokenId);
        return judgeTokenInRedis(realJwtId);
    }

    private Boolean judgeTokenInRedis(String tokenId){
        return redisUtil.exists(tokenId);
    }

    public Boolean removeAccessTokenInRedis(String accessToken){
        String realJwtId = getRealUserJwtIdForAccessToken(getUseridFormAccessToken(accessToken), getJwtIdFromAccessToken(accessToken));
        return redisUtil.remove(realJwtId);
    }
}
