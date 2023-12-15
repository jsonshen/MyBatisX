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
import static org.shenjia.mybatis.examples.dao.MultiColPkSupport.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.examples.entity.MultiColPk;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.PageAdapter;
import org.shenjia.mybatis.paging.RangeAdapter;

interface MultiColPkMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<MultiColPk>, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(qqNum, realName, nickname, password);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MultiColPkResult", value = {
        @Result(column="QQ_NUM", property="qqNum", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="REAL_NAME", property="realName", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="NICKNAME", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<MultiColPk> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MultiColPkResult")
    Optional<MultiColPk> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return count(null, completer);
    }

    default long count(String tableName, CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return delete(null, completer);
    }

    default int delete(String tableName, DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    default int deleteByPrimaryKey(Integer qqNum_, String realName_) {
        return deleteByPrimaryKey(null, qqNum_, realName_);
    }

    default int deleteByPrimaryKey(String tableName, Integer qqNum_, String realName_) {
        return delete(tableName, c -> 
            c.where(qqNum, isEqualTo(qqNum_))
            .and(realName, isEqualTo(realName_))
        );
    }

    default int insert(MultiColPk row) {
        return insert(null, row);
    }

    default int insert(String tableName, MultiColPk row) {
        return MyBatis3Utils.insert(this::insert, row, null == tableName ? multiColPk : multiColPk.withName(tableName), c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertMultiple(Collection<MultiColPk> records) {
        return insertMultiple(null, records);
    }

    default int insertMultiple(String tableName, Collection<MultiColPk> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, null == tableName ? multiColPk : multiColPk.withName(tableName), c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    default int insertSelective(MultiColPk row) {
        return insertSelective(null, row);
    }

    default int insertSelective(String tableName, MultiColPk row) {
        return MyBatis3Utils.insert(this::insert, row, null == tableName ? multiColPk : multiColPk.withName(tableName), c ->
            c.map(qqNum).toPropertyWhenPresent("qqNum", row::getQqNum)
            .map(realName).toPropertyWhenPresent("realName", row::getRealName)
            .map(nickname).toPropertyWhenPresent("nickname", row::getNickname)
            .map(password).toPropertyWhenPresent("password", row::getPassword)
        );
    }

    default Optional<MultiColPk> selectOne(SelectDSLCompleter completer) {
        return selectOne(selectList, completer);
    }

    default Optional<MultiColPk> selectOne(BasicColumn[] columns, SelectDSLCompleter completer) {
        return selectOne(null, columns, completer);
    }

    default Optional<MultiColPk> selectOne(String tableName, BasicColumn[] columns, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, columns, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    default List<MultiColPk> select(SelectDSLCompleter completer) {
        return select(selectList, completer);
    }

    default List<MultiColPk> select(BasicColumn[] columns, SelectDSLCompleter completer) {
        return select(null, columns, completer);
    }

    default List<MultiColPk> select(String tableName, BasicColumn[] columns, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, columns, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    default List<MultiColPk> selectDistinct(SelectDSLCompleter completer) {
        return selectDistinct(selectList, completer);
    }

    default List<MultiColPk> selectDistinct(BasicColumn[] columns, SelectDSLCompleter completer) {
        return selectDistinct(null, columns, completer);
    }

    default List<MultiColPk> selectDistinct(String tableName, BasicColumn[] columns, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, columns, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    default Optional<MultiColPk> selectByPrimaryKey(Integer qqNum_, String realName_) {
        return selectByPrimaryKey(selectList, qqNum_, realName_);
    }

    default Optional<MultiColPk> selectByPrimaryKey(BasicColumn[] columns, Integer qqNum_, String realName_) {
        return selectByPrimaryKey(null, columns, qqNum_, realName_);
    }

    default Optional<MultiColPk> selectByPrimaryKey(String tableName, BasicColumn[] columns, Integer qqNum_, String realName_) {
        return selectOne(tableName, columns, c ->
            c.where(qqNum, isEqualTo(qqNum_))
            .and(realName, isEqualTo(realName_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return update(null, completer);
    }

    default int update(String tableName, UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, null == tableName ? multiColPk : multiColPk.withName(tableName), completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(MultiColPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalTo(row::getQqNum)
                .set(realName).equalTo(row::getRealName)
                .set(nickname).equalTo(row::getNickname)
                .set(password).equalTo(row::getPassword);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(MultiColPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalToWhenPresent(row::getQqNum)
                .set(realName).equalToWhenPresent(row::getRealName)
                .set(nickname).equalToWhenPresent(row::getNickname)
                .set(password).equalToWhenPresent(row::getPassword);
    }

    default int updateByPrimaryKey(MultiColPk row) {
        return updateByPrimaryKey(null, row);
    }

    default int updateByPrimaryKey(String tableName, MultiColPk row) {
        return update(tableName, c ->
            c.set(nickname).equalTo(row::getNickname)
            .set(password).equalTo(row::getPassword)
            .where(qqNum, isEqualTo(row::getQqNum))
            .and(realName, isEqualTo(row::getRealName))
        );
    }

    default int updateByPrimaryKeySelective(MultiColPk row) {
        return updateByPrimaryKeySelective(null, row);
    }

    default int updateByPrimaryKeySelective(String tableName, MultiColPk row) {
        return update(tableName, c ->
            c.set(nickname).equalToWhenPresent(row::getNickname)
            .set(password).equalToWhenPresent(row::getPassword)
            .where(qqNum, isEqualTo(row::getQqNum))
            .and(realName, isEqualTo(row::getRealName))
        );
    }

    default Page<MultiColPk> selectPage(long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        return selectPage(selectList, currentPage, pageSize, where, sorts);
    }

    default Page<MultiColPk> selectPage(BasicColumn[] columns, long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        return selectPage(null, columns, currentPage, pageSize, where, sorts);
    }

    default Page<MultiColPk> selectPage(String tableName, BasicColumn[] columns, long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        Function<SelectModel, PageAdapter<MultiColPk>> adapter = model -> PageAdapter.of(model, this::count, this::selectMany, currentPage, pageSize);
        QueryExpressionDSL<PageAdapter<MultiColPk>> dsl = SelectDSL.select(adapter, columns)
            .from(null == tableName ? multiColPk : multiColPk.withName(tableName));
        Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));
        Optional.ofNullable(sorts).filter(ss -> ss.length > 0).ifPresent(ss -> dsl.orderBy(ss));
        return dsl.build().execute();
    }

    default List<MultiColPk> selectRange(long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        return selectRange(selectList, currentPage, pageSize, where, sorts);
    }

    default List<MultiColPk> selectRange(BasicColumn[] columns, long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        return selectRange(null, columns, currentPage, pageSize, where, sorts);
    }

    default List<MultiColPk> selectRange(String tableName, BasicColumn[] columns, long currentPage, int pageSize, WhereApplier where, SortSpecification... sorts) {
        Function<SelectModel, RangeAdapter<MultiColPk>> adapter = model -> RangeAdapter.of(model, this::selectMany, currentPage, pageSize);
        QueryExpressionDSL<RangeAdapter<MultiColPk>> dsl = SelectDSL.select(adapter, columns)
            .from(null == tableName ? multiColPk : multiColPk.withName(tableName));
        Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));
        Optional.ofNullable(sorts).filter(ss -> ss.length > 0).ifPresent(ss -> dsl.orderBy(ss));
        return dsl.build().execute();
    }
}