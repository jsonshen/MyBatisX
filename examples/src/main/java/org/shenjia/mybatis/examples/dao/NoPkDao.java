package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.spring.JdbcClient;
import org.shenjia.mybatis.spring.JdbcDao;
import org.springframework.stereotype.Component;

@Component
public class NoPkDao extends JdbcDao<NoPk> implements NoPkMapper {

    public NoPkDao(JdbcClient client) {
        super(client, new NoPk());
    }
}