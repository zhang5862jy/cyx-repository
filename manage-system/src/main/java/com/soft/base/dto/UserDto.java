package com.soft.base.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author: cyx
 * @Description: TODO
 * @DateTime: 2024/11/21 22:15
 **/
public class UserDto extends User {

    /**
     * 用户id
     */
    @Getter
    private Long id;

    /**
     * 部门id
     */
    @Getter
    private Long deptId;

    /**
     * 昵称
     */
    @Getter
    private String nickname;

    /**
     * 手机号码
     */
    @Getter
    private String phone;

    /**
     * 邮箱
     */
    @Getter
    private String email;

    @JsonCreator
    public UserDto(@JsonProperty("id") Long id, @JsonProperty("username") String username,
                   @JsonProperty("deptId") Long deptId, @JsonProperty("phone") String phone,
                   @JsonProperty("nickname") String nickname, @JsonProperty("email") String email,
                   @JsonProperty("password") String password, @JsonProperty("enabled") boolean enabled,
                   @JsonProperty("accountNonExpired") boolean accountNonExpired,
                   @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                   @JsonProperty("accountNonLocked") boolean accountNonLocked,
                   @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.phone = phone;
        this.nickname = nickname;
        this.email = email;
    }
}
