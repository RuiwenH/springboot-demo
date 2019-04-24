package com.reven.core;

/**
 * @ClassName:  ServiceException   
 * @Description:服务（业务）异常
 * @author reven
 * @date   2018年8月28日
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**   
     * @Fields code : 异常编码，用于判断或者国际化   
     */
    protected  String code;

    public ServiceException() {
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String code, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}