package com.scholar.mvc.properties;

import java.util.Properties;

import com.scholar.common.util.ClasspathPropertiesUtil;

/**
 * 工程全局属性
 *
 * @version 2.0
 * @author 小精灵
 * @date 2018年4月8日 下午10:04:40
 */
public class GlobalProperties {
    
    private static Properties properties = getProperties();

    /** 树的根节点 */
    public static final String ROOT = properties.getProperty("tree.root").trim();
    
    /** 文件上传路径 */
    public static final String FILE_UPLOAD = properties.getProperty("fileupload.filepath").trim();
    
    /** 是否关闭自动国际化 */
    public static final boolean INTERNATION = Boolean.valueOf(properties.getProperty("freemarker.internation").trim());
    
    /** 是否关闭自动国际化 */
    public static final boolean CACHE_TEMPLATE = Boolean.parseBoolean(properties.getProperty("freemarker.cacheTemplate").trim());
    
    /** freemarker后缀 */
    public static final String FREEMARKER_SUFFIX = properties.getProperty("freemarker.suffix").trim();
    
    private static Properties getProperties() {
        ClasspathPropertiesUtil ClasspathPropertiesUtil = new ClasspathPropertiesUtil("classpath:globle.properties");
        return ClasspathPropertiesUtil.getProperties();
    }
}
