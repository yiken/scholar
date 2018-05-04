package com.scholar.mvc.framework.message;

import org.springframework.stereotype.Component;

import com.scholar.mvc.framework.MvcConfig;
import com.scholar.mvc.framework.message.api.MessageProcesser;
import com.scholar.mvc.framework.message.enumaration.MessageType;

/** 
 * 视图转换器
 * @author yilean
 * @date 2018年5月4日 下午4:19:47  
 */
@Component(MvcConfig.PREFIX + "ViewProcesser")
public class ViewProcesser implements MessageProcesser {
    private Integer order = 0;

    @Override
    public Object process(Object message, MessageType messageType) {
        return message;
    }

    @Override
    public boolean support(MessageType messageType) {
        return messageType.equals(MessageType.VIEW);
    }

    
    /** @return the order */
    public Integer getOrder() {
        return order;
    }

    /** @param order the order to set */
    public void setOrder(Integer order) {
        this.order = order;
    }

}
