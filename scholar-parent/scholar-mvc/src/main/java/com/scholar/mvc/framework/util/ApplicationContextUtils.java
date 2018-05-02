package com.scholar.mvc.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取应用上下文
 *
 * @author yilean
 * @date 2018年4月27日 上午10:46:37
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 循环获取, 直到获取到为止
     * 
     * @return
     * @author yilean
     * @date 2018年4月27日 上午10:50:32
     */
    public static ApplicationContext getApplicationContext() {
        if (null == context) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // darling, something has happened!
                e.printStackTrace();
            }
            return getApplicationContext();
        }
        return context;
    }
}
