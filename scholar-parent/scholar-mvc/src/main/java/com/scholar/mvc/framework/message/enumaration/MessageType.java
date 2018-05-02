package com.scholar.mvc.framework.message.enumaration;

public enum MessageType {
    VIEW("view"),
    JSON("json");
    
    private String value;
    private MessageType(String value) {
        // TODO Auto-generated constructor stub
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
