package org.shenjia.mybatis.spring;

import static org.mybatis.dynamic.sql.SqlBuilder.countFrom;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.where.WhereApplier;
import org.springframework.util.StringUtils;

public abstract class JdbcMapper<T extends JdbcModel<T>> {

	protected JdbcClient client;
	protected T model;

	public JdbcMapper(JdbcClient client, T model) {
		this.client = client;
		this.model = model;
	}

	public Optional<T> selectOne(SelectDSLCompleter completer) {
		return selectOne(model.columns(), null, completer);
	}

	public Optional<T> selectOne(String tableName, SelectDSLCompleter completer) {
		return selectOne(model.columns(), tableName, completer);
	}

	public Optional<T> selectOne(Collection<? extends BasicColumn> columns, SelectDSLCompleter completer) {
		return selectOne(model.columns(), null, completer);
	}

	public Optional<T> selectOne(Collection<? extends BasicColumn> columns, String tableName,
	    SelectDSLCompleter completer) {
		return client.selectOne(selectStatement(columns, tableName, completer), model.rowMapper());
	}

	public List<T> selectList(SelectDSLCompleter completer) {
		return selectList(model.columns(), null, completer);
	}

	public List<T> selectList(String tableName, SelectDSLCompleter completer) {
		return selectList(model.columns(), tableName, completer);
	}

	public List<T> selectList(Collection<? extends BasicColumn> columns, SelectDSLCompleter completer) {
		return selectList(model.columns(), null, completer);
	}

	public List<T> selectList(Collection<? extends BasicColumn> columns, String tableName, SelectDSLCompleter completer) {
		return client.selectList(selectStatement(columns, tableName, completer), model.rowMapper());
	}
	
	public long count(CountDSLCompleter completer) {
		return count(null, completer);
	}

	public long count(String tableName, CountDSLCompleter completer) {
		AliasableSqlTable<?> table = model.table();
		if (StringUtils.hasText(tableName)) {
			table = table.withName(tableName);
		}
		return client.count(completer.apply(countFrom(table)));
	}

	public int delete(DeleteDSLCompleter completer) {
        return delete(null, completer);
    }

	public int delete(String tableName, DeleteDSLCompleter completer) {
		return client.delete(completer.apply(SqlBuilder.deleteFrom(targetTable(tableName))));
	}

    public int insert(T record) {
        return insert(null, record);
    }

    public abstract int insert(String tableName, T record);

    public int insertMultiple(Collection<T> records) {
        return insertMultiple(null, records);
    }

    public abstract int insertMultiple(String tableName, Collection<T> records);

    public int insertSelective(T record) {
        return insertSelective(null, record);
    }

    public abstract int insertSelective(String tableName, T record);

    public int update(UpdateDSLCompleter completer) {
        return update(null, completer);
    }

    public int update(String tableName, UpdateDSLCompleter completer) {
    	return client.update(completer.apply(SqlBuilder.update(targetTable(tableName))));
    }

    public int updateSelective(T record, WhereApplier applier) {
        return updateSelective(null, record, applier);
    }

    public abstract int updateSelective(String tableName, T record, WhereApplier applier);
    
    public int updateByPrimaryKey(T record) {
        return updateByPrimaryKey(null, record);
    }

    public abstract int updateByPrimaryKey(String tableName, T record);

    
    protected AliasableSqlTable<?> targetTable(String tableName) {
    	AliasableSqlTable<?> table = model.table();
		if (StringUtils.hasText(tableName)) {
			return table.withName(tableName);
		}
		return table;
    }
    
    @SuppressWarnings("unchecked")
	protected Buildable<SelectModel> selectStatement(Collection<? extends BasicColumn> columns, String tableName,
	    SelectDSLCompleter completer) {
		return completer.apply(select((Collection<BasicColumn>) columns).from(targetTable(tableName)));
	}
}
