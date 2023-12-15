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
package org.shenjia.mybatis.paging;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class DB2PagingDecorator implements
        PagingDecorator {

    @Override
    public SelectStatementProvider decorate(SelectStatementProvider delegate,
        long currentPage,
        int pageSize) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(delegate.getParameters());
        parameters.put("start_row", (currentPage - 1) * pageSize);
        parameters.put("end_row", currentPage * pageSize);

        String oldSql = delegate.getSelectStatement();
        StringBuilder sqlBuf = new StringBuilder(oldSql.length() + 162);
        sqlBuf.append("SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM (");
        sqlBuf.append(oldSql);
        sqlBuf.append(") AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN #{parameters.start_row} AND #{parameters.end_row}");
        String newSql = sqlBuf.toString();

        return new PagingSelectStatementProvider(parameters, newSql);
    }

}
