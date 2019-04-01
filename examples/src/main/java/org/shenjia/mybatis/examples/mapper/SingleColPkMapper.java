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

package org.shenjia.mybatis.examples.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static org.shenjia.mybatis.examples.mapper.SingleColPkDynamicSqlSupport.*;

import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.shenjia.mybatis.core.GenericMapper;
import org.shenjia.mybatis.examples.entity.SingleColPk;
import org.shenjia.mybatis.paging.PagingAdapter;

@Mapper
public interface SingleColPkMapper extends GenericMapper<SingleColPk, Integer> {
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
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("SingleColPkResult")
    SingleColPk selectOne(SelectStatementProvider selectStatement);

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
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(singleColPk);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, singleColPk);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer qqNum_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, singleColPk)
                .where(qqNum, isEqualTo(qqNum_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(SingleColPk record) {
        return insert(SqlBuilder.insert(record)
                .into(singleColPk)
                .map(qqNum).toProperty("qqNum")
                .map(realName).toProperty("realName")
                .map(nickname).toProperty("nickname")
                .map(password).toProperty("password")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(SingleColPk record) {
        return insert(SqlBuilder.insert(record)
                .into(singleColPk)
                .map(qqNum).toPropertyWhenPresent("qqNum", record::getQqNum)
                .map(realName).toPropertyWhenPresent("realName", record::getRealName)
                .map(nickname).toPropertyWhenPresent("nickname", record::getNickname)
                .map(password).toPropertyWhenPresent("password", record::getPassword)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<SingleColPk>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, qqNum, realName, nickname, password)
                .from(singleColPk);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<SingleColPk>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, qqNum, realName, nickname, password)
                .from(singleColPk);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default SingleColPk selectByPrimaryKey(Integer qqNum_) {
        return selectOneByExample()
                .where(qqNum, isEqualTo(qqNum_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(SingleColPk record) {
        return UpdateDSL.updateWithMapper(this::update, singleColPk)
                .set(qqNum).equalTo(record::getQqNum)
                .set(realName).equalTo(record::getRealName)
                .set(nickname).equalTo(record::getNickname)
                .set(password).equalTo(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(SingleColPk record) {
        return UpdateDSL.updateWithMapper(this::update, singleColPk)
                .set(qqNum).equalToWhenPresent(record::getQqNum)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(nickname).equalToWhenPresent(record::getNickname)
                .set(password).equalToWhenPresent(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(SingleColPk record) {
        return UpdateDSL.updateWithMapper(this::update, singleColPk)
                .set(realName).equalTo(record::getRealName)
                .set(nickname).equalTo(record::getNickname)
                .set(password).equalTo(record::getPassword)
                .where(qqNum, isEqualTo(record::getQqNum))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(SingleColPk record) {
        return UpdateDSL.updateWithMapper(this::update, singleColPk)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(nickname).equalToWhenPresent(record::getNickname)
                .set(password).equalToWhenPresent(record::getPassword)
                .where(qqNum, isEqualTo(record::getQqNum))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<PagingAdapter<List<SingleColPk>>> selectPageByExample(int limit, int offset) {
        return SelectDSL.select(selectModel -> PagingAdapter.of(selectModel, this::selectMany, limit, offset), qqNum, realName, nickname, password)
                .from(singleColPk);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<SingleColPk>> selectOneByExample() {
        return SelectDSL.selectWithMapper(this::selectOne, qqNum, realName, nickname, password)
                .from(singleColPk);
    }
}