package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.spring.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class MultiColPkDao extends MultiColPkMapper {
    public MultiColPkDao(JdbcClient client) {
        super(client);
    }
}