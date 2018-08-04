package com.http.exception;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class SessionException extends Exception {

    public String code;
    public String message;

    public SessionException(String msg, String code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }


}
