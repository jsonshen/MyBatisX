package org.shenjia.mybatis.paging;

import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;

public class LimitOffsetPagingDialect implements PagingDialect {

	@Override
	public String[] supportedDbProducts() {
		return new String[] { "MySQL", "H2", "MariaDB", "HSQLDB", "CUBRID", "PostgreSQL", "SQLite" };
	}

	@Override
	public SelectStatementProvider paging(RenderingStrategy renderer, Pageable pageable, List<BasicColumn> columns,
	    SqlTable table, WhereApplier where, SortSpecification... sorts) {
		long offset = (pageable.getCurrentPage() - 1) * pageable.getPageSize();
		return PagingDSL.query(select(columns).from(table), where, sorts)
		    .limit(pageable.getPageSize())
		    .offset(offset)
		    .build()
		    .render(renderer);
	}

}
