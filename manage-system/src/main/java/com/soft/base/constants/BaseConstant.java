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
     * 分片大小
     */
    public final static Long BURST_SIZE = 5l * 1024 * 1024;

    /**
     * 不分片
     */
    public final static Long BURST_FALSE = -1l;

    /**
     * 文件后缀的点
     */
    public final static String FILE_POINT_SUFFIX = ".";

    /**
     * 默认存储位置；minio
     */
    public final static String DEFAULT_STORAGE_LOCATION = "1";
}
