package org.shenjia.mybatis.sql.paging;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.shenjia.mybatis.paging.Pageable;
import org.shenjia.mybatis.paging.SkipFirstPagingDialect;

public class SkipFirstPagingDialectTest {

	@Test
	public void test_skip_first_paging() {
		SkipFirstPagingDialect dpd = new SkipFirstPagingDialect();
		Pageable pageable = new Pageable(10, 100);
		SqlTable table = SqlTable.of("tbl1");
		SqlColumn<String> col1 = table.column("col1");
		List<BasicColumn> columns = Arrays.asList(col1);
		SelectStatementProvider ssp = dpd.paging(RenderingStrategies.SPRING_NAMED_PARAMETER, pageable, columns, table,
		    w -> w.and(col1, SqlBuilder.isEqualTo("123")), col1);
		assertEquals(ssp.getSelectStatement(), "select skip :skip first :first col1 from tbl1 where col1 = :p1 order by col1");
		assertEquals(ssp.getParameters().size(), 3);
	}
}
