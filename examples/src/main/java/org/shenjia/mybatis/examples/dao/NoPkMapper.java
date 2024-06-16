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

import java.util.Collection;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.spring.JdbcMapper;

interface NoPkMapper extends JdbcMapper<NoPk> {
    default int insert(NoPk row) {
        return MyBatis3Utils.insert(this::insert, row, noPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertMultiple(Collection<NoPk> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, noPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertSelective(NoPk row) {
        return MyBatis3Utils.insert(this::insert, row, noPk, c ->
            c.map(qqNum).toPropertyWhenPresent("qqNum", row::getQqNum)
            .map(realName).toPropertyWhenPresent("realName", row::getRealName)
            .map(nickname).toPropertyWhenPresent("nickname", row::getNickname)
            .map(password).toPropertyWhenPresent("password", row::getPassword)
        );
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(NoPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalToWhenPresent(row::getQqNum)
                .set(realName).equalToWhenPresent(row::getRealName)
                .set(nickname).equalToWhenPresent(row::getNickname)
                .set(password).equalToWhenPresent(row::getPassword);
    }
}