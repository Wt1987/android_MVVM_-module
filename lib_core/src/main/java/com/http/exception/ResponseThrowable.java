package com.http.exception;

/**
 * Created by goldze on 2017/5/11.
 */

public class ResponseThrowable extends Exception {
    public String code;
    public String message;

    public ResponseThrowable(String msg, String code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }
}
