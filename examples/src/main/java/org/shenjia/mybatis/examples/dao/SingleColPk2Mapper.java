package org.shenjia.mybatis.examples.dao;

import java.util.Collection;

import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.examples.entity.SingleColPk2;
import org.shenjia.mybatis.spring.JdbcMapper;
import org.shenjia.mybatis.spring.JdbcClient;

class SingleColPk2Mapper extends JdbcMapper<SingleColPk2> {

	SingleColPk2Mapper(JdbcClient client) {
		super(client, new SingleColPk2());
	}

	@Override
	public int insert(String tableName, SingleColPk2 record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMultiple(String tableName, Collection<SingleColPk2> records) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(String tableName, SingleColPk2 record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSelective(String tableName, SingleColPk2 record, WhereApplier applier) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(String tableName, SingleColPk2 record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
