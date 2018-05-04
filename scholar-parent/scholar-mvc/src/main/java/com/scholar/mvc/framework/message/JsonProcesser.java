package com.scholar.mvc.framework.message;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.scholar.mvc.framework.message.api.MessageProcesser;
import com.scholar.mvc.framework.message.enumaration.MessageType;

/** 
 * json转换器
 * @author yilean
 * @date 2018年5月4日 下午4:30:50  
 */
public class JsonProcesser implements MessageProcesser {
    private Integer order = 1;

    @Override
    public Object process(Object message, MessageType messageType) {
        if (null == message) {
            return null;
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(message, headers, HttpStatus.OK);
        
        return responseEntity;
    }

    @Override
    public boolean support(MessageType messageType) {
        return messageType.equals(MessageType.JSON);
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
