package com.dely.chat.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JwtProperties {

    private static final String JWT_SECRET_KEY = "the_secret_key_of_dely_live_chat_software";
    public static String createJWT(Map<String, Object> claims) {

        //创建密钥即Key对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        return Jwts.builder()
                .setClaims(claims)
                // 设置签名使用的签名秘钥
                .signWith(secretKeySpec)
                .compact();
    }

    public static Claims parseJWT(String token) {

        //创建密钥即Key对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        return Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
