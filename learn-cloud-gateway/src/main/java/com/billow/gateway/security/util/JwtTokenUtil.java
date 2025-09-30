package com.billow.gateway.security.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billow.gateway.pojo.po.RolePo;
import com.billow.gateway.pojo.po.UserPo;
import com.billow.gateway.pojo.vo.UserRelationVo;
import com.billow.gateway.security.constant.AuthConstant;
import com.billow.gateway.security.vo.UserVo;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
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

    public String generateToken(UserRelationVo userRelationVo) {
        List<String> roleCodeList = userRelationVo.getRolePoList()
                .stream()
                .map(RolePo::getRoleCode)
                .collect(Collectors.toList());

        try {
            UserPo userPo = userRelationVo.getUserPo();
            JSONObject claims = new JSONObject();
            claims.put("id", userPo.getId());
            claims.put("username", userPo.getUsername());
            claims.put("usercode", userPo.getUsercode());
            // 创建JWT声明集
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userPo.getUsercode())
                    .issuer("Xiao-Y")
                    .issueTime(new Date())
                    .expirationTime(DateUtil.offsetDay(new Date(), 7)) // 7天过期
                    .claim(AuthConstant.AUTHORITY_CLAIM_NAME, roleCodeList)
                    .claim(AuthConstant.USER_INFO_CLAIM_NAME, claims)
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
        if (StringUtils.isEmpty(tokenString)) {
            return false;
        }

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
    public String getUsercodeFromToken(String token) {
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
        UserVo userVo = JSON.parseObject(JSON.toJSONString(claims.getClaim(AuthConstant.USER_INFO_CLAIM_NAME)), UserVo.class);
        userVo.setRoles(Convert.toList(String.class, claims.getListClaim(AuthConstant.AUTHORITY_CLAIM_NAME)));
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
            List<String> authorities = claims.getStringListClaim(AuthConstant.AUTHORITY_CLAIM_NAME);
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

    /**
     * 获取当前请求的tocken
     *
     * @return
     */
    public String getCurrrentToken(ServerHttpRequest request) {
        // 从 header 或其他位置提取认证信息
        String authHeader = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_TOKEN);

        if (authHeader != null && authHeader.startsWith(AuthConstant.BEARER_BLANK)) {
            String token = authHeader.substring(AuthConstant.BEARER_BLANK.length());
            return token;
        }
        return null;
    }
}
