package org.shenjia.mybatis.core;

import java.util.List;

import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.shenjia.mybatis.paging.PagingAdapter;

public interface GenericDao<Entity, PrimaryKey> {

    GenericMapper<Entity, PrimaryKey> getMapper();
    
    default long count(SelectStatementProvider selectStatement) {
        return getMapper().count(selectStatement);
    }

    default int delete(DeleteStatementProvider deleteStatement) {
        return getMapper().delete(deleteStatement);
    }

    default int insert(InsertStatementProvider<Entity> insertStatement) {
        return getMapper().insert(insertStatement);
    }

    default Entity selectOne(SelectStatementProvider selectStatement) {
        return getMapper().selectOne(selectStatement);
    }

    default List<Entity> selectMany(SelectStatementProvider selectStatement) {
        return getMapper().selectMany(selectStatement);
    }

    default int update(UpdateStatementProvider updateStatement) {
        return getMapper().update(updateStatement);
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return getMapper().countByExample();
    }

    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return getMapper().deleteByExample();
    }

    default int deleteByPrimaryKey(PrimaryKey primaryKey) {
        return getMapper().deleteByPrimaryKey(primaryKey);
    }

    default int insert(Entity record) {
        return getMapper().insert(record);
    }

    default int insertSelective(Entity record) {
        return getMapper().insertSelective(record);
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Entity>>> selectByExample() {
        return getMapper().selectByExample();
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Entity>>> selectDistinctByExample() {
        return getMapper().selectDistinctByExample();
    }

    default QueryExpressionDSL<PagingAdapter<List<Entity>>> selectPageByExample(int limit,
        int offset) {
        return getMapper().selectPageByExample(limit, offset);
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Entity>> selectOneByExample() {
        return getMapper().selectOneByExample();
    }

    default Entity selectByPrimaryKey(PrimaryKey primaryKey) {
        return getMapper().selectByPrimaryKey(primaryKey);
    }

    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(Entity record) {
        return getMapper().updateByExample(record);
    }

    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(Entity record) {
        return getMapper().updateByExampleSelective(record);
    }

    default int updateByPrimaryKey(Entity record) {
        return getMapper().updateByPrimaryKey(record);
    }

    default int updateByPrimaryKeySelective(Entity record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }
}
