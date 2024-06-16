package org.shenjia.mybatis.paging;

import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.where.WhereApplier;

class PagingDSL {

	static QueryExpressionDSL<SelectModel> query(QueryExpressionDSL<SelectModel> expr, WhereApplier where,
	    SortSpecification... sorts) {
		if (null != where) {
			expr.applyWhere(where);
		}
		if (null != sorts && sorts.length > 0) {
			expr.orderBy(sorts);
		}
		return expr;
	}
}
