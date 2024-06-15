package org.shenjia.mybatis.spring;

import java.util.List;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;
import org.springframework.jdbc.core.RowMapper;

public interface JdbcModel<T> {

	AliasableSqlTable<?> table();
	
	List<SqlColumn<?>> columns();
	
	RowMapper<T> rowMapper();
}
