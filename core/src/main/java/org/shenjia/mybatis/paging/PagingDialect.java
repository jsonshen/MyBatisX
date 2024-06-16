package org.shenjia.mybatis.paging;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;

public interface PagingDialect {

	String[] supportedDbProducts();

	SelectStatementProvider paging(RenderingStrategy renderer, Pageable pageable, List<BasicColumn> columns,
	    SqlTable table, WhereApplier where, SortSpecification... sorts);

}
