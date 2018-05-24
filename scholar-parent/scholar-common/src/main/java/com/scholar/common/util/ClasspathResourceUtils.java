package com.scholar.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

/**
 * 读取文件资源的工具</br>
 * 包括classpath路径
 * @author yiealen
 *
 */
public class ClasspathResourceUtils {

    /**
     * 读取文件资源到流
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        path = path.trim();
        if(path.startsWith("classpath:") && (path.charAt("classpath:".length())) == '/'){
            path = StringUtils.replaceOnce(path, "/", "");
        }
        try {
            URL url = ResourceUtils.getURL(path);
            System.out.println(url.getFile() + new File(url.getPath()));
            return url.openStream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读取文件资源到流
     * @param path
     * @return
     */
    public static File getResourceFile(String path){
        path = path.trim();
        if(path.startsWith("classpath:") && (path.charAt("classpath:".length())) == '/'){
            path = StringUtils.replaceOnce(path, "/", "");
        }
        try {
            URL url = ResourceUtils.getURL(path);
            return new File(url.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
