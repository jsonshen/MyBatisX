/*
 * Copyright (c) 2018-present, shenjia.org. All rights reserved.
 *
 * You may not use this file except in compliance with the License.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.shenjia.mybatis.examples.dao;

import static org.shenjia.mybatis.examples.dao.NoPkSupport.*;

import jakarta.annotation.Generated;
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
import org.shenjia.mybatis.examples.entity.NoPk;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.PageAdapter;
import org.shenjia.mybatis.paging.RangeAdapter;

// Do not modify this file, it will be overwritten when code is generated.
interface NoPkMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<NoPk>, CommonUpdateMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(qqNum, realName, nickname, password);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="NoPkResult", value = {
        @Result(column="QQ_NUM", property="qqNum", jdbcType=JdbcType.INTEGER),
        @Result(column="REAL_NAME", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="NICKNAME", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<NoPk> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("NoPkResult")
    Optional<NoPk> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return count(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(String tableName, CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return delete(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(String tableName, DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(NoPk row) {
        return insert(null, row);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(String tableName, NoPk row) {
        return MyBatis3Utils.insert(this::insert, row, null == tableName ? noPk : noPk.withName(tableName), c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<NoPk> records) {
        return insertMultiple(null, records);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(String tableName, Collection<NoPk> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, null == tableName ? noPk : noPk.withName(tableName), c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(NoPk row) {
        return insertSelective(null, row);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(String tableName, NoPk row) {
        return MyBatis3Utils.insert(this::insert, row, null == tableName ? noPk : noPk.withName(tableName), c ->
            c.map(qqNum).toPropertyWhenPresent("qqNum", row::getQqNum)
            .map(realName).toPropertyWhenPresent("realName", row::getRealName)
            .map(nickname).toPropertyWhenPresent("nickname", row::getNickname)
            .map(password).toPropertyWhenPresent("password", row::getPassword)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<NoPk> selectOne(SelectDSLCompleter completer) {
        return selectOne(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<NoPk> selectOne(String tableName, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> select(SelectDSLCompleter completer) {
        return select(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> select(String tableName, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> selectDistinct(SelectDSLCompleter completer) {
        return selectDistinct(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> selectDistinct(String tableName, SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return update(null, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(String tableName, UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, null == tableName ? noPk : noPk.withName(tableName), completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(NoPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalTo(row::getQqNum)
                .set(realName).equalTo(row::getRealName)
                .set(nickname).equalTo(row::getNickname)
                .set(password).equalTo(row::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(NoPk row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalToWhenPresent(row::getQqNum)
                .set(realName).equalToWhenPresent(row::getRealName)
                .set(nickname).equalToWhenPresent(row::getNickname)
                .set(password).equalToWhenPresent(row::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Page<NoPk> selectPage(long currentPage, int pageSize, WhereApplier where, SortSpecification... columns) {
        return selectPage(null, currentPage, pageSize, where, columns);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Page<NoPk> selectPage(String tableName, long currentPage, int pageSize, WhereApplier where, SortSpecification... columns) {
        Function<SelectModel, PageAdapter<NoPk>> adapter = model -> PageAdapter.of(model, this::count, this::selectMany, currentPage, pageSize);
        QueryExpressionDSL<PageAdapter<NoPk>> dsl = SelectDSL.select(adapter, qqNum, realName, nickname, password)
            .from(null == tableName ? noPk : noPk.withName(tableName));
        Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));
        Optional.ofNullable(columns).filter(cols -> cols.length > 0).ifPresent(cols -> dsl.orderBy(cols));
        return dsl.build().execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> selectRange(long currentPage, int pageSize, WhereApplier where, SortSpecification... columns) {
        return selectRange(null, currentPage, pageSize, where, columns);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<NoPk> selectRange(String tableName, long currentPage, int pageSize, WhereApplier where, SortSpecification... columns) {
        Function<SelectModel, RangeAdapter<NoPk>> adapter = model -> RangeAdapter.of(model, this::selectMany, currentPage, pageSize);
        QueryExpressionDSL<RangeAdapter<NoPk>> dsl = SelectDSL.select(adapter, qqNum, realName, nickname, password)
            .from(null == tableName ? noPk : noPk.withName(tableName));
        Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));
        Optional.ofNullable(columns).filter(cols -> cols.length > 0).ifPresent(cols -> dsl.orderBy(cols));
        return dsl.build().execute();
    }
}