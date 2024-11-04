package com.soft.base.constants;

/**
 * 正则常量
 */
public class RegexConstant {

    /**
     * 不包含中文正则
     */
    public final static String USERNAME_PATTERN = "^[\\x00-\\x7F]+$";

    /**
     * 角色编码以ROLE_开头正则
     */
    public final static String ROLE_CODE_HEADER = "^ROLE_.*";
}
