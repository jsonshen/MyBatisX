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

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.shenjia.mybatis.examples.entity.SingleColPk.SINGLE_COL_PK;

import java.util.Collection;
import java.util.Optional;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface SingleColPkMapper extends JdbcMapper<SingleColPk> {

    default int deleteByPrimaryKey(String tableName, Integer qqNum) {
        return delete(tableName, c -> 
            c.where(SINGLE_COL_PK.QQ_NUM, isEqualTo(qqNum))
        );
    }

    default int deleteByPrimaryKey(Integer qqNum) {
        return delete(c -> 
            c.where(SINGLE_COL_PK.QQ_NUM, isEqualTo(qqNum))
        );
    }

    default int insert(String tableName, SingleColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(SINGLE_COL_PK.QQ_NUM).toProperty("qqNum")
            .map(SINGLE_COL_PK.REAL_NAME).toProperty("realName")
            .map(SINGLE_COL_PK.NICKNAME).toProperty("nickname")
            .map(SINGLE_COL_PK.PASSWORD).toProperty("password")
            .map(SINGLE_COL_PK.BALANCE).toProperty("balance")
        );
    }

    default int insertSelective(String tableName, SingleColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(SINGLE_COL_PK.QQ_NUM).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(SINGLE_COL_PK.REAL_NAME).toPropertyWhenPresent("realName", record::getRealName)
            .map(SINGLE_COL_PK.NICKNAME).toPropertyWhenPresent("nickname", record::getNickname)
            .map(SINGLE_COL_PK.PASSWORD).toPropertyWhenPresent("password", record::getPassword)
            .map(SINGLE_COL_PK.BALANCE).toPropertyWhenPresent("balance", record::getBalance)
        );
    }

    default int insertMultiple(String tableName, Collection<SingleColPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(SINGLE_COL_PK.QQ_NUM).toProperty("qqNum")
            .map(SINGLE_COL_PK.REAL_NAME).toProperty("realName")
            .map(SINGLE_COL_PK.NICKNAME).toProperty("nickname")
            .map(SINGLE_COL_PK.PASSWORD).toProperty("password")
            .map(SINGLE_COL_PK.BALANCE).toProperty("balance")
        );
    }

    default Optional<SingleColPk> selectByPrimaryKey(String tableName, Integer qqNum) {
        return selectOne(tableName, c ->
            c.where(SINGLE_COL_PK.QQ_NUM, isEqualTo(qqNum))
        );
    }

    default Optional<SingleColPk> selectByPrimaryKey(Integer qqNum) {
        return selectOne(c ->
            c.where(SINGLE_COL_PK.QQ_NUM, isEqualTo(qqNum))
        );
    }

    default int updateByPrimaryKey(String tableName, SingleColPk record) {
        return update(tableName, c ->
            c.set(SINGLE_COL_PK.REAL_NAME).equalTo(record::getRealName)
            .set(SINGLE_COL_PK.NICKNAME).equalTo(record::getNickname)
            .set(SINGLE_COL_PK.PASSWORD).equalTo(record::getPassword)
            .set(SINGLE_COL_PK.BALANCE).equalTo(record::getBalance)
            .where(SINGLE_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKey(SingleColPk record) {
        return update(c ->
            c.set(SINGLE_COL_PK.REAL_NAME).equalTo(record::getRealName)
            .set(SINGLE_COL_PK.NICKNAME).equalTo(record::getNickname)
            .set(SINGLE_COL_PK.PASSWORD).equalTo(record::getPassword)
            .set(SINGLE_COL_PK.BALANCE).equalTo(record::getBalance)
            .where(SINGLE_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKeySelective(String tableName, SingleColPk record) {
        return update(c ->
            c.set(SINGLE_COL_PK.REAL_NAME).equalToWhenPresent(record::getRealName)
            .set(SINGLE_COL_PK.NICKNAME).equalToWhenPresent(record::getNickname)
            .set(SINGLE_COL_PK.PASSWORD).equalToWhenPresent(record::getPassword)
            .set(SINGLE_COL_PK.BALANCE).equalToWhenPresent(record::getBalance)
            .where(SINGLE_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
        );
    }

    default int updateByPrimaryKeySelective(SingleColPk record) {
        return update(c ->
            c.set(SINGLE_COL_PK.REAL_NAME).equalToWhenPresent(record::getRealName)
            .set(SINGLE_COL_PK.NICKNAME).equalToWhenPresent(record::getNickname)
            .set(SINGLE_COL_PK.PASSWORD).equalToWhenPresent(record::getPassword)
            .set(SINGLE_COL_PK.BALANCE).equalToWhenPresent(record::getBalance)
            .where(SINGLE_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
        );
    }
}