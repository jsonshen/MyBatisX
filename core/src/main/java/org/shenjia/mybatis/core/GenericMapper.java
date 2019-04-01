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

public interface GenericMapper<Entity, PrimaryKey> {

    default long count(SelectStatementProvider selectStatement){
        throw new NotImplementedException();
    }

    default int delete(DeleteStatementProvider deleteStatement){
        throw new NotImplementedException();
    }

    default int insert(InsertStatementProvider<Entity> insertStatement){
        throw new NotImplementedException();
    }

    default Entity selectOne(SelectStatementProvider selectStatement){
        throw new NotImplementedException();
    }

    default List<Entity> selectMany(SelectStatementProvider selectStatement){
        throw new NotImplementedException();
    }

    default int update(UpdateStatementProvider updateStatement){
        throw new NotImplementedException();
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        throw new NotImplementedException();
    }

    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        throw new NotImplementedException();
    }

    default int deleteByPrimaryKey(PrimaryKey primaryKey) {
        throw new NotImplementedException();
    }

    default int insert(Entity record) {
        throw new NotImplementedException();
    }

    default int insertSelective(Entity record) {
        throw new NotImplementedException();
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Entity>>> selectByExample() {
        throw new NotImplementedException();
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Entity>>> selectDistinctByExample() {
        throw new NotImplementedException();
    }

    default QueryExpressionDSL<PagingAdapter<List<Entity>>> selectPageByExample(int limit,
        int offset) {
        throw new NotImplementedException();
    }

    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Entity>> selectOneByExample() {
        throw new NotImplementedException();
    }

    default Entity selectByPrimaryKey(PrimaryKey primaryKey) {
        throw new NotImplementedException();
    }

    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(Entity record) {
        throw new NotImplementedException();
    }

    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(Entity record) {
        throw new NotImplementedException();
    }

    default int updateByPrimaryKey(Entity record) {
        throw new NotImplementedException();
    }

    default int updateByPrimaryKeySelective(Entity record) {
        throw new NotImplementedException();
    }
}