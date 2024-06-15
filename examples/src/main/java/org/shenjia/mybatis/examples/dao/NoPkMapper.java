package org.shenjia.mybatis.examples.dao;

import java.util.Collection;

import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcMapper;

class NoPkMapper extends JdbcMapper<NoPk> {

    public NoPkMapper(JdbcClient client) {
        super(client, new NoPk());
    }

	@Override
	public int insert(String tableName, NoPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMultiple(String tableName, Collection<NoPk> records) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(String tableName, NoPk record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSelective(String tableName, NoPk record, WhereApplier applier) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(String tableName, NoPk record) {
		// TODO Auto-generated method stub
		return 0;
	}
}