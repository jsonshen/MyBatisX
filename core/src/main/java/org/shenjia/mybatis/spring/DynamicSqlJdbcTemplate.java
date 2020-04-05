package org.shenjia.mybatis.spring;

import static org.mybatis.dynamic.sql.render.RenderingStrategies.SPRING_NAMED_PARAMETER;

import java.util.List;
import java.util.Map;

import org.mybatis.dynamic.sql.delete.DeleteModel;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.BatchInsertModel;
import org.mybatis.dynamic.sql.insert.InsertModel;
import org.mybatis.dynamic.sql.insert.render.BatchInsert;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

public class DynamicSqlJdbcTemplate {

    private NamedParameterJdbcOperations jdbcTemplate;

    public DynamicSqlJdbcTemplate(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <R> List<R> query(SelectModel selectModel,
        RowMapper<R> rowMapper) {
        SelectStatementProvider provider = selectModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.query(provider.getSelectStatement(), parameters, rowMapper);
    }

    public List<Map<String, Object>> queryForList(SelectModel selectModel) {
        SelectStatementProvider provider = selectModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.queryForList(provider.getSelectStatement(), parameters);
    }

    public <R> R queryForObject(SelectModel selectModel,
        Class<R> requiredType) {
        SelectStatementProvider provider = selectModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.queryForObject(provider.getSelectStatement(), parameters, requiredType);
    }

    public <R> R queryForObject(SelectModel selectModel,
        RowMapper<R> rowMapper) {
        SelectStatementProvider provider = selectModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.queryForObject(provider.getSelectStatement(), parameters, rowMapper);
    }

    public Map<String, Object> queryForMap(SelectModel selectModel) {
        SelectStatementProvider provider = selectModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.queryForMap(provider.getSelectStatement(), parameters);
    }

    public int update(UpdateModel updateModel) {
        UpdateStatementProvider provider = updateModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.update(provider.getUpdateStatement(), parameters);
    }

    public int delete(DeleteModel deleteModel) {
        DeleteStatementProvider provider = deleteModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new MapSqlParameterSource(provider.getParameters());
        return jdbcTemplate.update(provider.getDeleteStatement(), parameters);
    }

    public <T> int insert(InsertModel<T> insertModel) {
        InsertStatementProvider<T> provider = insertModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(provider.getRecord());
        return jdbcTemplate.update(provider.getInsertStatement(), parameters);
    }

    public <T> int[] batchInsert(BatchInsertModel<T> insertModel,
        List<T> records) {
        BatchInsert<T> provider = insertModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(records.toArray());
        return jdbcTemplate.batchUpdate(provider.getInsertStatementSQL(), batch);
    }

    @SafeVarargs
    public final <T> int[] batchInsert(BatchInsertModel<T> insertModel,
        T... records) {
        BatchInsert<T> provider = insertModel.render(SPRING_NAMED_PARAMETER);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(records);
        return jdbcTemplate.batchUpdate(provider.getInsertStatementSQL(), batch);
    }
}
