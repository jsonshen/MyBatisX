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
import static org.shenjia.mybatis.examples.entity.MultiColPk.MULTI_COL_PK;

import java.util.Collection;
import java.util.Optional;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface MultiColPkMapper extends JdbcMapper<MultiColPk> {

    default int deleteByPrimaryKey(Integer qqNum, String realName) {
        return delete(c -> 
            c.where(MULTI_COL_PK.QQ_NUM, isEqualTo(qqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(realName))
        );
    }

    default int deleteByPrimaryKey(String tableName, Integer qqNum, String realName) {
        return delete(tableName, c -> 
            c.where(MULTI_COL_PK.QQ_NUM, isEqualTo(qqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(realName))
        );
    }

    default int insert(String tableName, MultiColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(MULTI_COL_PK.QQ_NUM).toProperty("qqNum")
            .map(MULTI_COL_PK.REAL_NAME).toProperty("realName")
            .map(MULTI_COL_PK.NICKNAME).toProperty("nickname")
            .map(MULTI_COL_PK.PASSWORD).toProperty("password")
            .map(MULTI_COL_PK.BALANCE).toProperty("balance")
        );
    }

    default int insertSelective(String tableName, MultiColPk record) {
        return client().insert(SqlBuilder.insert(record)
        	.into(targetTable(tableName))
            .map(MULTI_COL_PK.QQ_NUM).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(MULTI_COL_PK.REAL_NAME).toPropertyWhenPresent("realName", record::getRealName)
            .map(MULTI_COL_PK.NICKNAME).toPropertyWhenPresent("nickname", record::getNickname)
            .map(MULTI_COL_PK.PASSWORD).toPropertyWhenPresent("password", record::getPassword)
            .map(MULTI_COL_PK.BALANCE).toPropertyWhenPresent("balance", record::getBalance)
        );
    }

    default int insertMultiple(String tableName, Collection<MultiColPk> records) {
        return client().insertMultiple(SqlBuilder.insertMultiple(records)
        	.into(targetTable(tableName))
            .map(MULTI_COL_PK.QQ_NUM).toProperty("qqNum")
            .map(MULTI_COL_PK.REAL_NAME).toProperty("realName")
            .map(MULTI_COL_PK.NICKNAME).toProperty("nickname")
            .map(MULTI_COL_PK.PASSWORD).toProperty("password")
            .map(MULTI_COL_PK.BALANCE).toProperty("balance")
        );
    }

    default Optional<MultiColPk> selectByPrimaryKey(Integer qqNum, String realName) {
        return selectOne(c ->
            c.where(MULTI_COL_PK.QQ_NUM, isEqualTo(qqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(realName))
        );
    }

    default Optional<MultiColPk> selectByPrimaryKey(String tableName, Integer qqNum, String realName) {
        return selectOne(tableName, c ->
            c.where(MULTI_COL_PK.QQ_NUM, isEqualTo(qqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(realName))
        );
    }

    default int updateByPrimaryKey(MultiColPk record) {
        return update(c ->
            c.set(MULTI_COL_PK.NICKNAME).equalTo(record::getNickname)
            .set(MULTI_COL_PK.PASSWORD).equalTo(record::getPassword)
            .set(MULTI_COL_PK.BALANCE).equalTo(record::getBalance)
            .where(MULTI_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKey(String tableName, MultiColPk record) {
        return update(tableName, c ->
            c.set(MULTI_COL_PK.NICKNAME).equalTo(record::getNickname)
            .set(MULTI_COL_PK.PASSWORD).equalTo(record::getPassword)
            .set(MULTI_COL_PK.BALANCE).equalTo(record::getBalance)
            .where(MULTI_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(MultiColPk record) {
        return update(c ->
            c.set(MULTI_COL_PK.NICKNAME).equalToWhenPresent(record::getNickname)
            .set(MULTI_COL_PK.PASSWORD).equalToWhenPresent(record::getPassword)
            .set(MULTI_COL_PK.BALANCE).equalToWhenPresent(record::getBalance)
            .where(MULTI_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(record::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(String tableName, MultiColPk record) {
        return update(c ->
            c.set(MULTI_COL_PK.NICKNAME).equalToWhenPresent(record::getNickname)
            .set(MULTI_COL_PK.PASSWORD).equalToWhenPresent(record::getPassword)
            .set(MULTI_COL_PK.BALANCE).equalToWhenPresent(record::getBalance)
            .where(MULTI_COL_PK.QQ_NUM, isEqualTo(record::getQqNum))
            .and(MULTI_COL_PK.REAL_NAME, isEqualTo(record::getRealName))
        );
    }
}