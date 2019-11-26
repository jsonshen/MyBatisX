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

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static org.shenjia.mybatis.examples.dao.SingleColPkSupport.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.PageAdapter;
import org.shenjia.mybatis.paging.RangeAdapter;

// Do not modify this file, it will be overwritten when code is generated.
interface SingleColPkMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(qqNum, realName, nickname, password);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<SingleColPk> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<SingleColPk> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("SingleColPkResult")
    Optional<SingleColPk> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="SingleColPkResult", value = {
        @Result(column="QQ_NUM", property="qqNum", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="REAL_NAME", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="NICKNAME", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<SingleColPk> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer qqNum_) {
        return delete(c -> 
            c.where(qqNum, isEqualTo(qqNum_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(SingleColPk record) {
        return MyBatis3Utils.insert(this::insert, record, singleColPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<SingleColPk> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, singleColPk, c ->
            c.map(qqNum).toProperty("qqNum")
            .map(realName).toProperty("realName")
            .map(nickname).toProperty("nickname")
            .map(password).toProperty("password")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(SingleColPk record) {
        return MyBatis3Utils.insert(this::insert, record, singleColPk, c ->
            c.map(qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
            .map(realName).toPropertyWhenPresent("realName", record::getRealName)
            .map(nickname).toPropertyWhenPresent("nickname", record::getNickname)
            .map(password).toPropertyWhenPresent("password", record::getPassword)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<SingleColPk> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<SingleColPk> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<SingleColPk> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<SingleColPk> selectByPrimaryKey(Integer qqNum_) {
        return selectOne(c ->
            c.where(qqNum, isEqualTo(qqNum_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, singleColPk, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(SingleColPk record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalTo(record::getQqNum)
                .set(realName).equalTo(record::getRealName)
                .set(nickname).equalTo(record::getNickname)
                .set(password).equalTo(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(SingleColPk record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(qqNum).equalToWhenPresent(record::getQqNum)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(nickname).equalToWhenPresent(record::getNickname)
                .set(password).equalToWhenPresent(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(SingleColPk record) {
        return update(c ->
            c.set(realName).equalTo(record::getRealName)
            .set(nickname).equalTo(record::getNickname)
            .set(password).equalTo(record::getPassword)
            .where(qqNum, isEqualTo(record::getQqNum))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(SingleColPk record) {
        return update(c ->
            c.set(realName).equalToWhenPresent(record::getRealName)
            .set(nickname).equalToWhenPresent(record::getNickname)
            .set(password).equalToWhenPresent(record::getPassword)
            .where(qqNum, isEqualTo(record::getQqNum))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<SingleColPk> selectRange(long currentPage, int pageSize) {
        return SelectDSL.select(selectModel -> RangeAdapter.of(selectModel, this::selectMany, currentPage, pageSize), qqNum, realName, nickname, password)
                .from(singleColPk)
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Page<SingleColPk> selectPage(long currentPage, int pageSize) {
        return SelectDSL.select(selectModel -> PageAdapter.of(selectModel, this::count, this::selectMany, currentPage, pageSize), qqNum, realName, nickname, password)
                .from(singleColPk)
                .build()
                .execute();
    }
}