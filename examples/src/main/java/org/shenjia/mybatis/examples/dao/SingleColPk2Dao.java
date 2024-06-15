package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.spring.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class SingleColPk2Dao extends SingleColPk2Mapper {

	public SingleColPk2Dao(JdbcClient client) {
		super(client);
	}

}
