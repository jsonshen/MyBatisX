package org.shenjia.mybatis.examples.dao;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.shenjia.mybatis.examples.entity.SingleColPk2.TABLE;

import java.util.Collection;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.SingleColPk2;
import org.shenjia.mybatis.spring.JdbcMapper;

//@formatter:off
interface SingleColPk2Mapper extends JdbcMapper<SingleColPk2> {
	
	default int deleteByPrimaryKey(Integer qqNum_, String realName_) {
        return delete(c -> 
            c.where(TABLE.qqNum, isEqualTo(qqNum_))
            .and(TABLE.realName, isEqualTo(realName_))
        );
    }

	default int insert(String tableName, SingleColPk2 record) {
		return client().insert(SqlBuilder.insert(record)
    		.into(targetTable(tableName))
	        .map(TABLE.qqNum).toProperty("qqNum")
	        .map(TABLE.realName).toProperty("realName")
	        .map(TABLE.nickname).toProperty("nickname")
	        .map(TABLE.password).toProperty("password")
        );
	}

	default int insertSelective(String tableName, SingleColPk2 record) {
		return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(TABLE.realName).toPropertyWhenPresent("realName", record::getRealName)
            .map(TABLE.nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(TABLE.password).toPropertyWhenPresent("password", record::getPassword)
        );
	}

	default int insertMultiple(String tableName, Collection<SingleColPk2> records) {
		return client().insertMultiple(SqlBuilder.insertMultiple(records)
			.into(targetTable(tableName))
			.map(TABLE.qqNum).toProperty("qqNum")
	        .map(TABLE.realName).toProperty("realName")
	        .map(TABLE.nickname).toProperty("nickname")
	        .map(TABLE.password).toProperty("password")
		);
	}
}
