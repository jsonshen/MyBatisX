// @formatter:off
/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shenjia.mybatis.examples.dao;

import static org.shenjia.mybatis.examples.entity.NoPk.NO_PK;

import java.util.Collection;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface NoPkMapper extends JdbcMapper<NoPk> {

    default int insert(String tableName, NoPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(NO_PK.QQ_NUM).toProperty("qqNum")
            .map(NO_PK.REAL_NAME).toProperty("realName")
            .map(NO_PK.NICKNAME).toProperty("nickname")
            .map(NO_PK.PASSWORD).toProperty("password")
            .map(NO_PK.BALANCE).toProperty("balance")
        );
    }

    default int insertSelective(String tableName, NoPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(NO_PK.QQ_NUM).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(NO_PK.REAL_NAME).toPropertyWhenPresent("realName", record::getRealName)
            .map(NO_PK.NICKNAME).toPropertyWhenPresent("nickname", record::getNickname)
            .map(NO_PK.PASSWORD).toPropertyWhenPresent("password", record::getPassword)
            .map(NO_PK.BALANCE).toPropertyWhenPresent("balance", record::getBalance)
        );
    }

    default int insertMultiple(String tableName, Collection<NoPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(NO_PK.QQ_NUM).toProperty("qqNum")
            .map(NO_PK.REAL_NAME).toProperty("realName")
            .map(NO_PK.NICKNAME).toProperty("nickname")
            .map(NO_PK.PASSWORD).toProperty("password")
            .map(NO_PK.BALANCE).toProperty("balance")
        );
    }
}