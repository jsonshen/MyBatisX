package org.shenjia.mybatis.paging;

import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.sql.SkipFirst;

public class SkipFirstPagingDialect implements PagingDialect {

	@Override
	public String[] supportedDbProducts() {
		return new String[] { "Firebird", "Informix" };
	}

	@Override
	public SelectStatementProvider paging(RenderingStrategy renderer, Pageable pageable,
	    List<BasicColumn> columns, SqlTable table, WhereApplier where, SortSpecification... sorts) {
		long offset = (pageable.getCurrentPage() - 1) * pageable.getPageSize();
		columns.set(0, SkipFirst.of(columns.get(0), offset, pageable.getPageSize(), renderer));
		return PagingDSL.query(select(columns).from(table), where, sorts).build().render(renderer);
	}

}
