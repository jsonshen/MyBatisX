package org.shenjia.mybatis.paging;

import static org.mybatis.dynamic.sql.SqlBuilder.isBetween;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.DerivedColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;

public class DB2PagingDialect implements PagingDialect {

	@Override
	public String[] supportedDbProducts() {
		return new String[] { "DB2" };
	}

	@Override
	public SelectStatementProvider paging(RenderingStrategy renderer, Pageable pageable, List<BasicColumn> columns,
	    SqlTable table, WhereApplier where, SortSpecification... sorts) {
		long startRow = (pageable.getCurrentPage() - 1) * pageable.getPageSize();
		long endRow = startRow + pageable.getPageSize();
		DerivedColumn<Long> rowNum = DerivedColumn.of("rownumber() over() as row_num");
		List<BasicColumn> fields = new ArrayList<>(columns);
		fields.add(rowNum);
		return select(columns).from(PagingDSL.query(select(fields).from(table), where, sorts))
		    .where(rowNum, isBetween(startRow).and(endRow))
		    .build()
		    .render(renderer);
	}

}
