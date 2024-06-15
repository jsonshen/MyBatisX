package org.shenjia.mybatis.spring;

import org.mybatis.dynamic.sql.util.spring.NamedParameterJdbcTemplateExtensions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcClient extends NamedParameterJdbcTemplateExtensions {

	public JdbcClient(JdbcTemplate template) {
		super(new NamedParameterJdbcTemplate(template));
	}
	
	public JdbcClient(NamedParameterJdbcTemplate template) {
		super(template);
	}

}
