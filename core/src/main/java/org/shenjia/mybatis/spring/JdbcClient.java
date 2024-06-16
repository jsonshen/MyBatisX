package org.shenjia.mybatis.spring;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.LogFactory;
import org.mybatis.dynamic.sql.util.spring.NamedParameterJdbcTemplateExtensions;
import org.springframework.jdbc.core.JdbcTemplate;
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

}
