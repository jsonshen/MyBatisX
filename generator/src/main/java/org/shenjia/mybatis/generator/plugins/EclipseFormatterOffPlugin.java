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

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;

public class EclipseFormatterOffPlugin extends MyBatisXPlugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable table) {
		interfaze.addFileCommentLine("// @formatter:off");
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
		clazz.addFileCommentLine("// @formatter:off");
		return true;
	}

	@Override
	public boolean dynamicSqlSupportGenerated(TopLevelClass clazz, IntrospectedTable table) {
		clazz.addFileCommentLine("// @formatter:off");
		return true;
	}
	
}
