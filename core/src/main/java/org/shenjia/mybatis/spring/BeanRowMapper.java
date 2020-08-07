package org.shenjia.mybatis.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

public class BeanRowMapper<T> implements RowMapper<T> {

    private static final Log LOG = LogFactory.getLog(BeanRowMapper.class);

    private Class<T> beanClass;
    private Map<String, Method> methodMap;

    public BeanRowMapper(Class<T> beanClass) {
        this.beanClass = beanClass;
        this.methodMap = getMehtodMap(beanClass);
    }
    
    public static <R> BeanRowMapper<R> of(Class<R> beanClass) {
        return new BeanRowMapper<R>(beanClass);
    }

    @Override
    public T mapRow(ResultSet rs,
            int rowNum) throws SQLException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(">> beanClass: " + beanClass);
        }
        T bean;
        try {
            bean = (T) beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnName = metaData.getColumnName(i);
            String methodName = buildMethodName(columnName);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">> columnName: " + columnName + ", methodName: " + methodName);
            }
            try {
                Method method = methodMap.get(methodName);
                Class<?> parameterType = method.getParameterTypes()[0];
                if (null == parameterType) {
                    continue;
                }
                String typeName = parameterType.getName();
                if (String.class.isAssignableFrom(parameterType)) {
                    method.invoke(bean, rs.getString(columnName));
                } else if (BigDecimal.class.isAssignableFrom(parameterType)) {
                    method.invoke(bean, rs.getBigDecimal(columnName));
                } else if (Integer.class.isAssignableFrom(parameterType) || "int".equals(typeName)) {
                    method.invoke(bean, rs.getInt(columnName));
                } else if (Long.class.isAssignableFrom(parameterType) || "long".equals(typeName)) {
                    method.invoke(bean, rs.getLong(columnName));
                } else if (Boolean.class.isAssignableFrom(parameterType) || "boolean".equals(typeName)) {
                    method.invoke(bean, rs.getBoolean(columnName));
                } else if (Float.class.isAssignableFrom(parameterType) || "float".equals(typeName)) {
                    method.invoke(bean, rs.getFloat(columnName));
                } else if (Double.class.isAssignableFrom(parameterType) || "double".equals(typeName)) {
                    method.invoke(bean, rs.getDouble(columnName));
                } else if (Short.class.isAssignableFrom(parameterType) || "short".equals(typeName)) {
                    method.invoke(bean, rs.getShort(columnName));
                } else if (Byte.class.isAssignableFrom(parameterType) || "byte".equals(typeName)) {
                    method.invoke(bean, rs.getByte(columnName));
                } else if (byte[].class.isAssignableFrom(parameterType)) {
                    method.invoke(bean, rs.getBytes(columnName));
                } else {
                    LOG.warn("Unsupported data type -> [" + parameterType + "]");
                }
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                LOG.warn("Failed to execute (" + methodName + ") method.", e);
            }

        }
        return bean;
    }

    private String buildMethodName(String columnName) {
        String[] parts = columnName.toLowerCase()
                .split("_");
        StringBuilder buf = new StringBuilder(30);
        buf.append("set");
        for (String part : parts) {
            buf.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1));
        }
        return buf.toString();
    }

    private Map<String, Method> getMehtodMap(Class<T> entityClass) {
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : entityClass.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && method.getParameterCount() == 1) {
                methodMap.put(methodName, method);
            }
        }
        return methodMap;
    }
}
