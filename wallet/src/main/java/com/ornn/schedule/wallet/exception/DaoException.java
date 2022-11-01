package com.ornn.schedule.wallet.exception;

/**
 * @author: CANHUI.WANG * @create: 2022-09-19
 */

public class DaoException extends RuntimeException {

    private final Integer code;

    public DaoException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DaoException(Integer code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
