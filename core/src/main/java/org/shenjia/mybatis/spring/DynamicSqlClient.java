/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shenjia.mybatis.spring;

import java.util.Map;
import java.util.Optional;

import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.util.spring.NamedParameterJdbcTemplateExtensions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DynamicSqlClient extends NamedParameterJdbcTemplateExtensions {

	public DynamicSqlClient(NamedParameterJdbcTemplate template) {
		super(template);
	}
    
	public <T> Optional<T> queryForObject(Buildable<SelectModel> selectStatement, Class<T> requiredType) {
		return super.selectOne(selectStatement, new SingleColumnRowMapper<T>(requiredType));
	}
	
	public <T> Optional<T> queryForEntity(Buildable<SelectModel> selectStatement, Class<T> requiredType) {
		return super.selectOne(selectStatement, new BeanPropertyRowMapper<T>(requiredType));
	}
	
	public Optional<Map<String, Object>> queryForMap(Buildable<SelectModel> selectStatement) {
		return super.selectOne(selectStatement, new ColumnMapRowMapper());
	}
}
