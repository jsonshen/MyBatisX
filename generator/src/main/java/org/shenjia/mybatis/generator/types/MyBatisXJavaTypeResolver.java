package org.shenjia.mybatis.generator.types;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class MyBatisXJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    public MyBatisXJavaTypeResolver() {
        super();
        //把数据库的 TINYINT 映射成 Integer
        addTypeMapping(Types.TINYINT, "TINYINT", Integer.class.getName());
    }

    private void addTypeMapping(int sqlType,
        String jdbcTypeName,
        String javaTypeName) {
        typeMap.put(sqlType, new JdbcTypeInformation(jdbcTypeName, new FullyQualifiedJavaType(javaTypeName)));
    }
}
