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
import static org.shenjia.mybatis.examples.entity.MultiColPk.TABLE;

import java.util.Collection;
import java.util.Optional;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface MultiColPkMapper extends JdbcMapper<MultiColPk> {
    default int deleteByPrimaryKey(String tableName, Integer qqNum, String realName) {
        return delete(tableName, c -> 
            c.where(TABLE.qqNum, isEqualTo(qqNum))
            .and(TABLE.realName, isEqualTo(realName))
        );
    }

    default int deleteByPrimaryKey(Integer qqNum, String realName) {
        return delete(c -> 
            c.where(TABLE.qqNum, isEqualTo(qqNum))
            .and(TABLE.realName, isEqualTo(realName))
        );
    }

    default int insert(String tableName, MultiColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }

    default int insertSelective(String tableName, MultiColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(TABLE.realName).toPropertyWhenPresent("realName", record::getRealName)
            .map(TABLE.nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(TABLE.password).toPropertyWhenPresent("password", record::getPassword)
        );
    }

    default int insertMultiple(String tableName, Collection<MultiColPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(TABLE.qqNum).toProperty("qqNum")
            .map(TABLE.realName).toProperty("realName")
            .map(TABLE.nickname).toProperty("nickname")
            .map(TABLE.password).toProperty("password")
        );
    }

    default Optional<MultiColPk> selectByPrimaryKey(Integer qqNum, String realName) {
        return selectOne(c ->
            c.where(TABLE.qqNum, isEqualTo(qqNum))
            .and(TABLE.realName, isEqualTo(realName))
        );
    }

    default Optional<MultiColPk> selectByPrimaryKey(String tableName, Integer qqNum, String realName) {
        return selectOne(tableName, c ->
            c.where(TABLE.qqNum, isEqualTo(qqNum))
            .and(TABLE.realName, isEqualTo(realName))
        );
    }

    default int updateByPrimaryKey(String tableName, MultiColPk record) {
        return update(tableName, c ->
            c.set(TABLE.nickname).equalTo(record::getNickname)
            .set(TABLE.password).equalTo(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
            .and(TABLE.realName, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKey(MultiColPk record) {
        return update(c ->
            c.set(TABLE.nickname).equalTo(record::getNickname)
            .set(TABLE.password).equalTo(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
            .and(TABLE.realName, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(MultiColPk record) {
        return update(c ->
            c.set(TABLE.nickname).equalToWhenPresent(record::getNickname)
            .set(TABLE.password).equalToWhenPresent(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
            .and(TABLE.realName, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(String tableName, MultiColPk record) {
        return update(c ->
            c.set(TABLE.nickname).equalToWhenPresent(record::getNickname)
            .set(TABLE.password).equalToWhenPresent(record::getPassword)
            .where(TABLE.qqNum, isEqualTo(record::getQqNum))
            .and(TABLE.realName, isEqualTo(record::getRealName))
        );
    }
}