package com.soft.base.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static com.soft.base.constants.BaseConstant.DEL_FLAG_EXIST;

@Configuration
public class MybatisPlusAutoConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建时间
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        // 自动填充创建人
        this.setFieldValByName("createBy", getCurrentUsername(), metaObject);
        // 自动填充更新时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        // 自动填充更新人
        this.setFieldValByName("updateBy", getCurrentUsername(), metaObject);
        // 自动填充逻辑删除
        this.setFieldValByName("delFlag", DEL_FLAG_EXIST, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充更新时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        // 自动填充更新人
        this.setFieldValByName("updateBy", getCurrentUsername(), metaObject);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 返回当前用户的用户名
        }
        return null; // 或者返回一个默认值
    }
}
