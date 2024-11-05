package com.soft.base.filter;

import com.soft.base.constants.HttpConstant;
import com.soft.base.utils.JwtUtil;
import com.soft.base.utils.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import static com.soft.base.constants.RedisConstant.TOKEN_BLACKLIST;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX;
import static com.soft.base.constants.TokenConstant.TOKEN_PREFIX_LENGTH;

/**
 * JWT过滤器
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final RedisTemplate<String,String> redisTemplate;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil,
                            UserDetailsService userDetailsService,
                            RedisTemplate<String,String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwt = null;

        try {
            // 检查 Token
            if (StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
                // 去除token前缀
                jwt = token.substring(TOKEN_PREFIX_LENGTH);
            }

            // 验证当前token是否存在于黑名单中
            Set<String> members = redisTemplate.opsForSet().members(TOKEN_BLACKLIST);
            if (members != null && !members.isEmpty() && members.contains(token)) {
                ResponseUtil.writeErrMsg(response, HttpConstant.UNAUTHORIZED, "token已加入黑名单");
                return;
            }

            // 验证token是否过期
            if (jwtUtil.validateToken(jwt)) {
                ResponseUtil.writeErrMsg(response, HttpConstant.UNAUTHORIZED, "token过期");
                return;
            }

            username = jwtUtil.extractUsername(jwt);

            // 如果用户名未被设置且 Spring Security 的上下文中没有认证信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = (User) this.userDetailsService.loadUserByUsername(username);

                // 在 Spring Security 中设置用户身份
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("context already setted...");
            }
            // token有效或者没有token时，放行
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            ResponseUtil.writeErrMsg(response, HttpConstant.UNAUTHORIZED, "token过期");
        } finally {
            // 清除安全上下文
            SecurityContextHolder.clearContext();
            log.info("securityContextHolder cleared...");
        }
    }
}
