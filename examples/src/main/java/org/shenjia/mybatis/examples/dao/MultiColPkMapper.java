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

import java.util.Collection;
import java.util.Optional;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface MultiColPkMapper extends JdbcMapper<MultiColPk> {
    default int deleteByPrimaryKey(Integer qqNum_, String realName_) {
        return delete(c -> 
            c.where(qqNum, isEqualTo(qqNum_))
            .and(realName, isEqualTo(realName_))
        );
    }

    default int insert(MultiColPk row) {
        return MyBatis3Utils.insert(this::insert, row, multiColPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertMultiple(Collection<MultiColPk> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, multiColPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertSelective(MultiColPk row) {
        return MyBatis3Utils.insert(this::insert, row, multiColPk, c ->
            c.map(qqNum).toPropertyWhenPresent("qqNum", row::getQqNum)
            .map(realName).toPropertyWhenPresent("realName", row::getRealName)
            .map(nickname).toPropertyWhenPresent("nickname", row::getNickname)
            .map(password).toPropertyWhenPresent("password", row::getPassword)
        );
    }

    default Optional<MultiColPk> selectByPrimaryKey(Integer qqNum_, String realName_) {
        return selectOne(c ->
            c.where(qqNum, isEqualTo(qqNum_))
            .and(realName, isEqualTo(realName_))
        );
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(MultiColPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalToWhenPresent(row::getQqNum)
                .set(realName).equalToWhenPresent(row::getRealName)
                .set(nickname).equalToWhenPresent(row::getNickname)
                .set(password).equalToWhenPresent(row::getPassword);
    }

    default int updateByPrimaryKey(MultiColPk row) {
        return update(c ->
            c.set(nickname).equalTo(row::getNickname)
            .set(password).equalTo(row::getPassword)
            .where(qqNum, isEqualTo(row::getQqNum))
            .and(realName, isEqualTo(row::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(MultiColPk row) {
        return update(c ->
            c.set(nickname).equalToWhenPresent(row::getNickname)
            .set(password).equalToWhenPresent(row::getPassword)
            .where(qqNum, isEqualTo(row::getQqNum))
            .and(realName, isEqualTo(row::getRealName))
        );
    }
}