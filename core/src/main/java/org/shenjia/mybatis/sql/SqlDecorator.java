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
