package org.shenjia.mybatis.sql;

public class SqlException extends RuntimeException {

    private static final long serialVersionUID = -387511186215032760L;

    public SqlException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
