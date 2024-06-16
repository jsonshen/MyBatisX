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

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.shenjia.mybatis.examples.entity.SingleColPk.TABLE;

import java.util.Collection;
import java.util.Optional;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface SingleColPkMapper extends JdbcMapper<SingleColPk> {
    default int deleteByPrimaryKey(Integer qqNum) {
        return delete(c -> 
            c.where(TABLE.qqNum, isEqualTo(qqNum))
        );
    }

    default int deleteByPrimaryKey(String tableName, Integer qqNum) {
        return delete(tableName, c -> 
            c.where(TABLE.qqNum, isEqualTo(qqNum))
        );
    }

    default int insert(String tableName, SingleColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }

    default int insertSelective(String tableName, SingleColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(TABLE.realName).toPropertyWhenPresent("realName", record::getRealName)
            .map(TABLE.nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(TABLE.password).toPropertyWhenPresent("password", record::getPassword)
        );
    }

    default int insertMultiple(String tableName, Collection<SingleColPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }

    default Optional<SingleColPk> selectByPrimaryKey(String tableName, Integer qqNum) {
        return selectOne(tableName, c ->
            c.where(TABLE.qqNum, isEqualTo(qqNum))
        );
    }

    default Optional<SingleColPk> selectByPrimaryKey(Integer qqNum) {
        return selectOne(c ->
            c.where(TABLE.qqNum, isEqualTo(qqNum))
        );
    }

    default int updateByPrimaryKey(SingleColPk record) {
        return update(c ->
            c.set(TABLE.realName).equalTo(record::getRealName)
            .set(TABLE.nickname).equalTo(record::getNickname)
            .set(TABLE.password).equalTo(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKey(String tableName, SingleColPk record) {
        return update(tableName, c ->
            c.set(TABLE.realName).equalTo(record::getRealName)
            .set(TABLE.nickname).equalTo(record::getNickname)
            .set(TABLE.password).equalTo(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKeySelective(String tableName, SingleColPk record) {
        return update(c ->
            c.set(TABLE.realName).equalToWhenPresent(record::getRealName)
            .set(TABLE.nickname).equalToWhenPresent(record::getNickname)
            .set(TABLE.password).equalToWhenPresent(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKeySelective(SingleColPk record) {
        return update(c ->
            c.set(TABLE.realName).equalToWhenPresent(record::getRealName)
            .set(TABLE.nickname).equalToWhenPresent(record::getNickname)
            .set(TABLE.password).equalToWhenPresent(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
        );
    }
}