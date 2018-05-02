package com.scholar.mvc.core;

import java.io.Serializable;

/**
 *
 * @author yilean
 * @date 2018年4月27日 下午12:33:19
 */
public class ServiceResult implements Serializable {
    /** long-ServiceResult.java */
    private static final long serialVersionUID = -7346580741801390800L;
    private String            code;
    private Object            result;
    private String            message;

    public ServiceResult() {}

    public ServiceResult(String code) {
        this.code = code;
    }
    
    public static ServiceResult newInstance(String code) {
        return new ServiceResult(code);
    }

    public ServiceResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public static ServiceResult newInstance(String code, String message) {
        return new ServiceResult(code, message);
    }

    public ServiceResult(String code, Object result) {
        this.code = code;
        this.result = result;
    }
    
    public static ServiceResult newInstance(String code, Object result) {
        return new ServiceResult(code, result);
    }

    public ServiceResult(String code, Object result, String message) {
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public static ServiceResult newInstance(String code, Object result, String message) {
        return new ServiceResult(code, result, message);
    }

    
    /***************************** getter/setter ***************************/
    
    
    /** @return the code */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return the result */
    public Object getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /** @return the message */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
