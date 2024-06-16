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
// @formatter:off
package org.shenjia.mybatis.examples.dao;

import static org.shenjia.mybatis.examples.entity.NoPk.TABLE;

import java.util.Collection;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface NoPkMapper extends JdbcMapper<NoPk> {
    default int insert(String tableName, NoPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }

    default int insertSelective(String tableName, NoPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(TABLE.realName).toPropertyWhenPresent("realName", record::getRealName)
            .map(TABLE.nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(TABLE.password).toPropertyWhenPresent("password", record::getPassword)
        );
    }

    default int insertMultiple(String tableName, Collection<NoPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }
}