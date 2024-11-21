package com.soft.base.permission;

import com.soft.base.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: cyx
 * @Description: 权限校验
 * @DateTime: 2024/11/20 19:23
 **/
@Service(value = "cps")
public class CustomPermissionService {

    private final SecurityUtil securityUtil;

    @Autowired
    public CustomPermissionService(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    public boolean hasPermission(String... permissions) {
        if (permissions == null) {
            return false;
        }
        List<String> permission = securityUtil.getPermission();
        if (permission == null) {
            return false;
        }

        return permission
                .stream()
                .filter(StringUtils::hasText)
                .anyMatch(item -> PatternMatchUtils.simpleMatch(permissions, item));
    }
}
