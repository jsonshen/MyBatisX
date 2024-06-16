package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.examples.entity.SingleColPk2;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcDao;
import org.springframework.stereotype.Component;

@Component
public class SingleColPk2Dao extends JdbcDao<SingleColPk2> implements SingleColPk2Mapper {

	public SingleColPk2Dao(JdbcClient client) {
		super(client, new SingleColPk2());
	}

}
