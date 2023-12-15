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
package org.shenjia.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;

public class EqualsAndHashCodePlugin extends MyBatisXPlugin {
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
		EqualsHashCodeGenerator generator = new EqualsHashCodeGenerator();
		generator.setProperties(properties);
		generator.setContext(context);
		return generator.modelBaseRecordClassGenerated(clazz, table);
	}
	
	private static class EqualsHashCodeGenerator extends EqualsHashCodePlugin {
		
		@Override
		public boolean modelBaseRecordClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
			List<IntrospectedColumn> columns = table.getPrimaryKeyColumns();
			if (null == columns || columns.isEmpty()) {
				return super.modelBaseRecordClassGenerated(clazz, table);
			}
			generateEquals(clazz, table.getPrimaryKeyColumns(), table);
			generateHashCode(clazz, table.getPrimaryKeyColumns(), table);
			return true;
		}
	}
}
