package org.shenjia.mybatis.examples.dao;

import java.util.Collection;

import org.shenjia.mybatis.examples.entity.SingleColPk2;
import org.shenjia.mybatis.spring.JdbcMapper;

interface SingleColPk2Mapper extends JdbcMapper<SingleColPk2> {

	default int insert(String tableName, SingleColPk2 record) {
		return 0;
	}

	default int insertSelective(String tableName, SingleColPk2 record) {
		return 0;
	}

	default int insertMultiple(String tableName, Collection<SingleColPk2> records) {
		return 0;
	}
}
