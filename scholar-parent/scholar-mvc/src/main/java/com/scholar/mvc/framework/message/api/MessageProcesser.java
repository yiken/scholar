package com.scholar.mvc.framework.message.api;

import com.scholar.mvc.framework.message.enumaration.MessageType;

/**
 * 消息转换
 * 
 * @author yilean
 * @date 2018年5月4日 下午4:18:03 
 * @param <T>
 */
public interface MessageProcesser extends Comparable<MessageProcesser> {
    Integer getOrder();

    Object process(Object message, MessageType messageType);
    
    boolean support(MessageType messageType);
    
    @Override
    public default int compareTo(MessageProcesser messageProcesser) {
        return this.getOrder().compareTo(messageProcesser.getOrder());
    }
}
