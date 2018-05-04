package com.scholar.mvc.framework.message.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.scholar.mvc.framework.message.api.MessageProcesser;
import com.scholar.mvc.framework.message.enumaration.MessageType;
import com.scholar.mvc.framework.util.ApplicationContextUtils;

@Component
public class MessageProcesserRegistry {

    private List<MessageProcesser> messageProcessers;

    // 初始化
    // 查找所有消息转换器
    @PostConstruct
    public void init() {
        Map<String, MessageProcesser> processers = ApplicationContextUtils.getApplicationContext().getBeansOfType(MessageProcesser.class);
        if (null == processers) {
            return;
        }
        Collection<MessageProcesser> values = processers.values();
        messageProcessers = new LinkedList<>(values);
        Collections.sort(messageProcessers);//重排序
    }

    /**
     * 执行消息转换
     * 
     * @param result
     * @param messageType
     * @return
     * @author yilean
     * @date 2018年4月27日 上午11:32:56
     */
    public Object processMessage(Object result, MessageType messageType) {
        for (MessageProcesser messageProcesser : messageProcessers) {
            if (messageProcesser.support(messageType)) {
                return messageProcesser.process(result, messageType);
            }
        }
        return null;
    }
}
