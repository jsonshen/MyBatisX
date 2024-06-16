package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcDao;
import org.springframework.stereotype.Component;

@Component
public class MultiColPkDao extends JdbcDao<MultiColPk> implements MultiColPkMapper {

    public MultiColPkDao(JdbcClient client) {
        super(client, new MultiColPk());
    }
}