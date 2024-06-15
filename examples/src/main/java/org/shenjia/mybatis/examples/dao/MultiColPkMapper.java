package org.shenjia.mybatis.examples.dao;

import java.util.Collection;

import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcMapper;

class MultiColPkMapper extends JdbcMapper<MultiColPk> {

    public MultiColPkMapper(JdbcClient client) {
        super(client, new MultiColPk());
    }

	@Override
	public int insert(String tableName, MultiColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMultiple(String tableName, Collection<MultiColPk> records) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(String tableName, MultiColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSelective(String tableName, MultiColPk record, WhereApplier applier) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(String tableName, MultiColPk record) {
		// TODO Auto-generated method stub
		return 0;
	}
}