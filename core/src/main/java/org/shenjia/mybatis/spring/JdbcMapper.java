package org.shenjia.mybatis.spring;

import static org.mybatis.dynamic.sql.SqlBuilder.countColumn;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.CountDSL;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.where.WhereApplier;
import org.shenjia.mybatis.paging.DialectAdapter;
import org.shenjia.mybatis.paging.Page;
import org.shenjia.mybatis.paging.Pageable;
import org.shenjia.mybatis.sql.Columns;
import org.springframework.util.StringUtils;

public interface JdbcMapper<T extends JdbcModel<T>> {

	JdbcClient client();

	T model();

	int insert(String tableName, T record);

	int insertSelective(String tableName, T record);

	int insertMultiple(String tableName, Collection<T> records);

	default Optional<T> selectOne(SelectDSLCompleter completer) {
		return selectOne(model().columns(), null, completer);
	}

	default Optional<T> selectOne(String tableName, SelectDSLCompleter completer) {
		return selectOne(model().columns(), tableName, completer);
	}

	default Optional<T> selectOne(Collection<? extends BasicColumn> columns, SelectDSLCompleter completer) {
		return selectOne(model().columns(), null, completer);
	}

	default Optional<T> selectOne(Collection<? extends BasicColumn> columns, String tableName,
	    SelectDSLCompleter completer) {
		return client().selectOne(selectStatement(columns, tableName, completer), model().rowMapper());
	}

	default List<T> selectList(SelectDSLCompleter completer) {
		return selectList(model().columns(), null, completer);
	}

	default List<T> selectList(String tableName, SelectDSLCompleter completer) {
		return selectList(model().columns(), tableName, completer);
	}

	default List<T> selectList(Collection<? extends BasicColumn> columns, SelectDSLCompleter completer) {
		return selectList(model().columns(), null, completer);
	}

	default List<T> selectList(Collection<? extends BasicColumn> columns, String tableName,
	    SelectDSLCompleter completer) {
		return client().selectList(selectStatement(columns, tableName, completer), model().rowMapper());
	}

	default Page<T> selectPage(Pageable pageable, WhereApplier where, SortSpecification... sorts) {
		return selectPage(model().columns(), pageable, where, sorts);
	}

	default Page<T> selectPage(List<? extends BasicColumn> columns, Pageable pageable, WhereApplier where,
	    SortSpecification... sorts) {
		return selectPage(null, columns, pageable, where, sorts);
	}

	default Page<T> selectPage(String tableName, List<? extends BasicColumn> columns, Pageable pageable,
	    WhereApplier where, SortSpecification... sorts) {
		CountDSL<SelectModel> stmt = countColumn(Columns.ONE).from(targetTable(tableName));
		if (null != where) {
			stmt.applyWhere(where);
		}
		long total = client().count(stmt);
		Page<T> page = new Page<>(pageable.getCurrentPage(), pageable.getPageSize(), total);
		if (total > 0 && ((page.getCurrentPage() - 1) * page.getPageSize() < total)) {
			page.setData(selectRange(tableName, columns, pageable, where, sorts));
		}
		return page;
	}

	default List<T> selectRange(Pageable pageable, WhereApplier where, SortSpecification... sorts) {
		return selectRange(model().columns(), pageable, where, sorts);
	}

	default List<T> selectRange(List<? extends BasicColumn> columns, Pageable pageable, WhereApplier where,
	    SortSpecification... sorts) {
		return selectRange(null, columns, pageable, where, sorts);
	}

	default List<T> selectRange(String tableName, List<? extends BasicColumn> columns, Pageable pageable,
	    WhereApplier where, SortSpecification... sorts) {
		SelectStatementProvider stmt = DialectAdapter.adapt(client().getMetadata(),
		    RenderingStrategies.SPRING_NAMED_PARAMETER, pageable, columns, targetTable(tableName), where, sorts);
		return client().selectList(stmt, model().rowMapper());
	}

	default long count(CountDSLCompleter completer) {
		return count(null, completer);
	}

	default long count(String tableName, CountDSLCompleter completer) {
		AliasableSqlTable<?> table = model().table();
		if (StringUtils.hasText(tableName)) {
			table = table.withName(tableName);
		}
		return client().count(completer.apply(countColumn(Columns.ONE).from(table)));
	}

	default int delete(DeleteDSLCompleter completer) {
		return delete(null, completer);
	}

	default int delete(String tableName, DeleteDSLCompleter completer) {
		return client().delete(completer.apply(SqlBuilder.deleteFrom(targetTable(tableName))));
	}

	default int insert(T record) {
		return insert(null, record);
	}

	default int insertSelective(T record) {
		return insertSelective(null, record);
	}

	default int insertMultiple(Collection<T> records) {
		return insertMultiple(null, records);
	}

	default int update(UpdateDSLCompleter completer) {
		return update(null, completer);
	}

	default int update(String tableName, UpdateDSLCompleter completer) {
		return client().update(completer.apply(SqlBuilder.update(targetTable(tableName))));
	}

	default AliasableSqlTable<?> targetTable(String tableName) {
		AliasableSqlTable<?> table = model().table();
		if (StringUtils.hasText(tableName)) {
			return table.withName(tableName);
		}
		return table;
	}

	@SuppressWarnings("unchecked")
	default Buildable<SelectModel> selectStatement(Collection<? extends BasicColumn> columns, String tableName,
	    SelectDSLCompleter completer) {
		return completer.apply(select((Collection<BasicColumn>) columns).from(targetTable(tableName)));
	}

}
