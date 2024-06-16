package org.shenjia.mybatis.paging;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.WhereApplier;

public final class DialectAdapter {

	private static final DialectAdapter INSTANCE = new DialectAdapter();

	private Map<String, PagingDialect> dialectMappings;

	private DialectAdapter() {
		Log log = LogFactory.getLog(DialectAdapter.class);
		String clz = System.getProperty(PagingDialect.class.getName());
		this.dialectMappings = Optional.ofNullable(clz).map(dialect -> {
			try {
				Class<?> cls = Class.forName(dialect);
				if (PagingDialect.class.isAssignableFrom(cls)) {
					Map<String, PagingDialect> items = new HashMap<>();
					PagingDialect pd = (PagingDialect) cls.newInstance();
					for (String product : pd.supportedDbProducts()) {
						items.put(product.toUpperCase(), pd);
					}
					return items;
				}
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
			return null;
		}).orElseGet(() -> {
			Map<String, PagingDialect> items = new HashMap<>();
			ServiceLoader<PagingDialect> dialects = ServiceLoader.load(PagingDialect.class);
			for (PagingDialect pd : dialects) {
				for (String product : pd.supportedDbProducts()) {
					items.put(product.toUpperCase(), pd);
				}
			}
			return items;
		});
		this.dialectMappings.forEach((key, val) -> log.debug("Paging[" + key + "]: " + val));
	}

	@SuppressWarnings("unchecked")
	public static SelectStatementProvider adapt(DatabaseMetaData metadata, RenderingStrategy renderer,
	    Pageable pageable, List<? extends BasicColumn> columns, SqlTable table, WhereApplier where,
	    SortSpecification... sorts) {
		String dbpn;
		try {
			dbpn = metadata.getDatabaseProductName();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to obtain database product name, metadata is '" + metadata + "'", e);
		}
		PagingDialect dialect = INSTANCE.dialectMappings.get(dbpn.toUpperCase());
		if (null == dialect) {
			throw new RuntimeException("Paging dialect not found, metadata is '" + metadata + "'");
		}
		return dialect.paging(renderer, pageable, (List<BasicColumn>) columns, table, where, sorts);
	}
}
