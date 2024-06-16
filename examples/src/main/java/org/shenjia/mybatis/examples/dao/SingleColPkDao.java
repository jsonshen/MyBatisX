package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcDao;
import org.springframework.stereotype.Component;

@Component
public class SingleColPkDao extends JdbcDao<SingleColPk> implements SingleColPkMapper {

    public SingleColPkDao(JdbcClient client) {
        super(client, new SingleColPk());
    }
}