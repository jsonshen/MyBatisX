package org.shenjia.mybatis.paging;

import static org.mybatis.dynamic.sql.SqlBuilder.isGreaterThan;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThanOrEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.DerivedColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;

public class OraclePagingDialect implements PagingDialect {

	@Override
	public String[] supportedDbProducts() {
		return new String[] { "Oracle" };
	}

	@Override
	public SelectStatementProvider paging(RenderingStrategy renderer, Pageable pageable, List<BasicColumn> columns,
	    SqlTable table, WhereApplier where, SortSpecification... sorts) {
		long startRow = (pageable.getCurrentPage() - 1) * pageable.getPageSize();
		long endRow = startRow + pageable.getPageSize();
		List<BasicColumn> fields = new ArrayList<>(columns);
		fields.add(DerivedColumn.of("rownum row_num"));
		WhereApplier wa = Optional.ofNullable(where)
		    .map(w -> w.andThen(c -> c.and(DerivedColumn.of("rownum"), isLessThanOrEqualTo(endRow))))
		    .orElseGet(() -> w -> w.and(DerivedColumn.of("rownum"), isLessThanOrEqualTo(endRow)));
		return select(columns).from(PagingDSL.query(select(fields).from(table), wa, sorts))
		    .where(DerivedColumn.of("row_num"), isGreaterThan(startRow))
		    .build()
		    .render(renderer);
	}

}
