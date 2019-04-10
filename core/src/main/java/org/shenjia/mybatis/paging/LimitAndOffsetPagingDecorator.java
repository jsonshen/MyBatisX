package org.shenjia.mybatis.paging;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

class LimitAndOffsetPagingDecorator implements
        PagingDecorator {

    @Override
    public SelectStatementProvider decorate(SelectStatementProvider delegate,
        long currentPage,
        int pageSize) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(delegate.getParameters());
        parameters.put("limit", pageSize);
        parameters.put("offset", (currentPage - 1) * pageSize);

        String oldSql = delegate.getSelectStatement();
        StringBuilder sqlBuf = new StringBuilder(oldSql.length() + 54);
        sqlBuf.append(oldSql);
        sqlBuf.append(" limit #{parameters.limit} offset #{parameters.offset}");
        String newSql = sqlBuf.toString();

        return new PagingSelectStatementProvider(parameters, newSql);
    }

}
