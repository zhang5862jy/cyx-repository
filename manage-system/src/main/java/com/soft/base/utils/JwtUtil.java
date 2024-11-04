package com.soft.base.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value(value = "${jwt.secret-key}")
    private String secretKey;

    @Value(value = "${jwt.expire}")
    private Long expirationTime;

    private final BeanUtil beanUtil;

    @Autowired
    public JwtUtil(BeanUtil beanUtil) {
        this.beanUtil = beanUtil;
    }

    // 生成 Token
    public String generateToken(String username, User claims) {
        return createToken(claims, username);
    }

    // 创建 Token
    private String createToken(User claims, String subject) {
        return Jwts.builder()
                // 设置附加信息（用户信息）
                .setClaims(claims == null ? new HashMap<>() : beanUtil.bean2map(claims))
                // 设置主题（用户名）
                .setSubject(subject)
                // 设置token生效时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置token过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                // 使用 HS256 算法（HMAC-SHA256）对 JWT 进行签名
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 验证 Token
    public boolean validateToken(String token) {
        return isTokenExpired(token);
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 检查 Token 是否过期
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // 提取所有声明
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
