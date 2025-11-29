package org.shenjia.mybatis.spring;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.logging.LogFactory;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.util.spring.NamedParameterJdbcTemplateExtensions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcClient extends NamedParameterJdbcTemplateExtensions {

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
        return selectOne(selectStatement, new ColumnMapRowMapper());
    }

	public <T> Optional<T> selectForEntity(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectOne(selectStatement, new BeanPropertyRowMapper<T>(mappedClass));
    }
	
	public <T> Optional<T> selectForObject(Buildable<SelectModel> selectStatement, Class<T> mappedClass) {
        return selectOne(selectStatement, new SingleColumnRowMapper<T>(mappedClass));
    }
}
