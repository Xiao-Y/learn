//package com.billow.auth.security.config;
//
//import com.billow.auth.security.vo.SecurityUser;
//import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
//import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
//import org.springframework.stereotype.Component;
//
///**
// * JWT内容增强器
// */
//@Component
//public class JwtTokenEnhancer implements OAuth2TokenCustomizer<JwtEncodingContext> {
//    @Override
//    public void customize(JwtEncodingContext context) {
//        // 只在access token时添加额外信息
//        if (context.getTokenType().getValue().equals("access_token")) {
//            Object principal = context.getPrincipal();
//            if (principal instanceof SecurityUser) {
//                SecurityUser securityUser = (SecurityUser) principal;
//
//                // 添加自定义声明到JWT中
//                context.getClaims()
//                        .claim("id", securityUser.getId())
//                        .claim("usercode", securityUser.getUsercode());
//            }
//        }
//    }
////    @Override
////    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
////        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
////        Map<String, Object> info = new HashMap<>();
////        //把用户ID设置到JWT中
////        info.put("id", securityUser.getId());
////        info.put("usercode",securityUser.getUsercode());
////        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
////        return accessToken;
////    }
//
//
//}