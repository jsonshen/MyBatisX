package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.spring.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class SingleColPkDao extends SingleColPkMapper {
    public SingleColPkDao(JdbcClient client) {
        super(client);
    }
}