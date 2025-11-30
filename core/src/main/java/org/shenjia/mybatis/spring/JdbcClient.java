package org.shenjia.mybatis.spring;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.apache.commons.logging.LogFactory;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.util.spring.NamedParameterJdbcTemplateExtensions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcClient extends NamedParameterJdbcTemplateExtensions {
	
	private static final Map<Class<?>, RowMapper<?>> ROW_MAPPERS = new ConcurrentHashMap<>();
	private static final ColumnMapRowMapper MAP_ROW_MAPPER = new ColumnMapRowMapper();

	private DatabaseMetaData metadata;

	public DatabaseMetaData getMetadata() {
		return metadata;
	}

	public JdbcClient(JdbcTemplate template) {
		this(new NamedParameterJdbcTemplate(template));
	}

	public JdbcClient(NamedParameterJdbcTemplate template) {
		super(template);
		DataSource ds = template.getJdbcTemplate().getDataSource();
		Connection con = DataSourceUtils.getConnection(ds);
		try {
			this.metadata = con.getMetaData();
		} catch (SQLException e) {
			LogFactory.getLog(JdbcClient.class).error("Failed to obtain DatabaseMetaData", e);
		} finally {
			DataSourceUtils.releaseConnection(con, ds);
		}
	}
	
	public Optional<Map<String, Object>> selectForMap(Buildable<SelectModel> selectStatement) {
        return selectOne(selectStatement, MAP_ROW_MAPPER);
    }

	public <T> Optional<T> selectForEntity(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectOne(selectStatement, getRowMapper(mappedClass, () -> new BeanPropertyRowMapper<T>(mappedClass)));
    }
	
	public <T> Optional<T> selectForObject(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectOne(selectStatement, getRowMapper(mappedClass, () -> new SingleColumnRowMapper<T>(mappedClass)));
    }
	
	public List<Map<String, Object>> selectForMaps(Buildable<SelectModel> selectStatement) {
        return selectList(selectStatement, MAP_ROW_MAPPER);
    }

	public <T> List<T> selectForEntities(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectList(selectStatement, getRowMapper(mappedClass, () -> new BeanPropertyRowMapper<T>(mappedClass)));
    }
	
	public <T> List<T> selectForObjects(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectList(selectStatement, getRowMapper(mappedClass, () -> new SingleColumnRowMapper<T>(mappedClass)));
    }
	
	@SuppressWarnings("unchecked")
	private static <T> RowMapper<T> getRowMapper(Class<T> mappedClass, Supplier<RowMapper<T>> supplier) {
		RowMapper<?> rowMapper = ROW_MAPPERS.get(mappedClass);
		if (null == rowMapper) {
			rowMapper = supplier.get();
			ROW_MAPPERS.put(mappedClass, rowMapper);
		}
		return (RowMapper<T>) rowMapper;
	}
}
