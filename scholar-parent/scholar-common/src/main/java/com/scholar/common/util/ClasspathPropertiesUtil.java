package com.scholar.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 读取classpath下的资源文件
 *
 * @version 2.0
 * @author 小精灵
 * @date 2018年4月9日 上午12:47:00
 */
public class ClasspathPropertiesUtil {
    private String properiesName = "";

    public ClasspathPropertiesUtil() {

    }
    public ClasspathPropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }
    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = ClasspathResourceUtils.getResourceAsStream(properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return value;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = ClasspathResourceUtils.getResourceAsStream(properiesName);
            p.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return p;
    }

    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(properiesName);
            p.load(is);
            os = new FileOutputStream(ClasspathResourceUtils.getResourceFile(properiesName));

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        ClasspathPropertiesUtil p = new ClasspathPropertiesUtil("sysConfig.properties");
        p.writeProperty("namess", "wang");
    }

}
