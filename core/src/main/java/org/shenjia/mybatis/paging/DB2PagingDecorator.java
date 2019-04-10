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
