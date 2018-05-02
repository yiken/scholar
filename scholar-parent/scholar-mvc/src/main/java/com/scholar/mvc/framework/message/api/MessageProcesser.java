package com.scholar.mvc.framework.message.api;

import com.scholar.mvc.framework.message.enumaration.MessageType;

public interface MessageProcesser {

    Object process(Object message, MessageType messageType);
    
    boolean support(MessageType messageType);
}
