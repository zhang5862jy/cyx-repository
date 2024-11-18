package com.soft.base.constants;

public class BaseConstant {

    /**
     * 默认启用
     */
    public final static Integer DEF_STATUS = 1;

    /**
     * 逻辑删除——存在
     */
    public final static String DEL_FLAG_EXIST = "1";

    /**
     * 逻辑删除——删除
     */
    public final static String DEL_FLAG_UNEXIST = "0";

    /**
     * 是否启用——启用
     */
    public final static Integer ENABLED_TRUE = 1;

    /**
     * 是否启用——禁用
     */
    public final static Integer ENABLED_FALSE = 0;

    /**
     * 左下划线
     */
    public final static String LEFT_SLASH = "/";

    /**
     * 全通配符
     */
    public final static String ALL_WILDCARD_CHARACTER = "**";

    /**
     * 转义符
     */
    public final static String ESCAPE_CHARACTER = "\\";

    /**
     * 空白符
     */
    public final static String BLANK_CHARACTER = "";

    /**
     * 分片大小
     */
    public final static Long BURST_SIZE = 5L * 1024 * 1024;

    /**
     * 不分片
     */
    public final static Long BURST_FALSE = -1L;

    /**
     * 文件后缀的点
     */
    public final static String FILE_POINT_SUFFIX = ".";

    /**
     * 默认存储位置；minio
     */
    public final static String DEFAULT_STORAGE_LOCATION = "1";

    /**
     * 默认管理员角色
     */
    public final static String MANAGER_ROLE_CODE = "ROLE_ADMIN";

    /**
     * 字典类型；启用
     */
    public final static String DICT_TYPE_STATUS_ENABLE = "1";

    /**
     * 字典类型；禁用
     */
    public final static String DICT_TYPE_STATUS_FORBIDDEN = "0";

    /**
     * 登录方式：密码
     */
    public final static String LOGIN_METHOD_PASSWORD = "password";

    /**
     * 登录方式：邮箱
     */
    public final static String LOGIN_METHOD_EMAIL = "email";

    /**
     * 登录方式：邮箱验证码长度
     */
    public final static Integer LOGIN_CAPTCHAT_LENGTH = 6;
}
