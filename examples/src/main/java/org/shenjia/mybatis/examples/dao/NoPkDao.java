package org.shenjia.mybatis.examples.dao;

import org.shenjia.mybatis.spring.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class NoPkDao extends NoPkMapper {
    public NoPkDao(JdbcClient client) {
        super(client);
    }
}