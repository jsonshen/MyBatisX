package org.shenjia.mybatis.core;

public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
