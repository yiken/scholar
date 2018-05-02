/**   
 * @title FactoryHelper.java 
 * @package com.scholar.mvc.framework.util 
 */
package com.scholar.mvc.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/** 
 * bean获取工具
 * @author yilean
 * @date 2018年4月27日 上午11:07:57  
 */

public class FactoryHelper implements BeanFactoryAware {

    private static BeanFactory factory;
    
    /* 
     * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }
    
    /**
     * 循环获取, 直到取到为止
     * @return     
     * @author yilean
     * @date 2018年4月27日 上午11:10:40
     */
    public static BeanFactory getBeanFactory() {
        if (null == factory) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // darling, something has happened!
                e.printStackTrace();
            }
            return getBeanFactory();
        }
        return factory;
    }

}
