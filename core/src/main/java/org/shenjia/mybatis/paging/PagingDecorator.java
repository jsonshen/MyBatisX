package org.shenjia.mybatis.paging;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public interface PagingDecorator {

    SelectStatementProvider decorate(SelectStatementProvider delegate,
        long currentPage,
        int pageSize);
    
}
