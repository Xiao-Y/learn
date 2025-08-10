package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.gateway.security.vo.UserVo;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Autowired
    private KeyPair keyPair;

    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        try {
            // 创建JWT声明集
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .issuer("Xiao-Y")
                    .issueTime(new Date())
                    .expirationTime(DateUtil.offsetDay(new Date(), 7)) // 7天过期
                    .claim("authorities", roles)
                    .claim("user_name", userDetails.getUsername())
                    .build();

            // 创建带有RS256算法的头部
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            // 创建签名JWT
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);

            // 使用私钥签名
            RSASSASigner signer = new RSASSASigner(keyPair.getPrivate());
            signedJWT.sign(signer);

            // 返回JWT字符串
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public Boolean validateToken(String tokenString) {

        try {
            // 解析JWT字符串
            SignedJWT signedJWT = SignedJWT.parse(tokenString);

            // 创建验证器（使用公钥）
            RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyPair.getPublic());

            // 验证签名
            boolean isSignatureValid = signedJWT.verify(verifier);

            // 检查是否过期
            boolean isNotExpired = new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());

            return isSignatureValid && isNotExpired;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取JWT声明集中的用户名
     *
     * @param token JWT字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * 获取JWT声明集中的用户名
     *
     * @param token JWT字符串
     * @return 用户名
     */
    public UserVo getUserVoFromToken(String token) throws ParseException {
        JWTClaimsSet claims = getAllClaimsFromToken(token);
        String userInfo = claims.getStringClaim("user_info");
        UserVo userVo = new UserVo();
        JSONObject userJsonObject = JSONObject.parseObject(userInfo);
        userVo.setId(Convert.toLong(userJsonObject.get("id")));
        userVo.setUsername(userJsonObject.getString("user_name"));
        userVo.setUsercode(userJsonObject.getString("usercode"));
        userVo.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));
        return userVo;
    }

    /**
     * 获取JWT声明集中的角色列表
     *
     * @param token JWT字符串
     * @return 权限列表
     */
    public List<String> getAuthoritiesFromToken(String token) {
        try {
            JWTClaimsSet claims = getAllClaimsFromToken(token);
            List<String> authorities = claims.getStringListClaim("authorities");
            return authorities;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取JWT声明集
     *
     * @param token JWT字符串
     * @return JWT声明集
     */
    public JWTClaimsSet getAllClaimsFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT token", e);
        }
    }

}
