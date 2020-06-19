package org.shenjia.mybatis.sql;

import java.util.Map;

import org.mybatis.dynamic.sql.delete.render.DefaultDeleteStatementProvider;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.select.render.DefaultSelectStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.DefaultUpdateStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

@FunctionalInterface
public interface SqlDecorator {

    String decorate(String sql, Map<String, Object> parameters);
    
    default SelectStatementProvider buildSelect(SelectStatementProvider provider) {
        Map<String, Object> parameters = provider.getParameters();
        String sql = decorate(provider.getSelectStatement(), parameters);
        return new DefaultSelectStatementProvider.Builder().withSelectStatement(sql)
            .withParameters(parameters)
            .build();
    }
    
    default UpdateStatementProvider buildUpdate(UpdateStatementProvider provider) {
        Map<String, Object> parameters = provider.getParameters();
        String sql = decorate(provider.getUpdateStatement(), parameters);
        return new DefaultUpdateStatementProvider.Builder().withUpdateStatement(sql)
            .withParameters(parameters)
            .build();
    }
    
    default DeleteStatementProvider buildDelete(DeleteStatementProvider provider) {
        Map<String, Object> parameters = provider.getParameters();
        String sql = decorate(provider.getDeleteStatement(), parameters);
        return new DefaultDeleteStatementProvider.Builder().withDeleteStatement(sql)
            .withParameters(parameters)
            .build();
    }
}
