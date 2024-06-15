package org.shenjia.mybatis.examples.dao;

import java.util.Collection;

import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcMapper;

class SingleColPkMapper extends JdbcMapper<SingleColPk> {

    public SingleColPkMapper(JdbcClient client) {
        super(client, new SingleColPk());
    }

	@Override
	public int insert(String tableName, SingleColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMultiple(String tableName, Collection<SingleColPk> records) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(String tableName, SingleColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSelective(String tableName, SingleColPk record, WhereApplier applier) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(String tableName, SingleColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}
}