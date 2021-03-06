package org.shenjia.mybatis.paging;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

/**
 * Derby 10.6 or later
 * 
 * @author jason
 *
 */
public class DerbyPagingDecorator implements
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
        StringBuilder sqlBuf = new StringBuilder(oldSql.length() + 74);
        sqlBuf.append(oldSql);
        sqlBuf.append(" OFFSET #{parameters.offset} ROWS FETCH NEXT #{parameters.limit} ROWS ONLY");
        String newSql = sqlBuf.toString();

        return new PagingSelectStatementProvider(parameters, newSql);
    }

}
