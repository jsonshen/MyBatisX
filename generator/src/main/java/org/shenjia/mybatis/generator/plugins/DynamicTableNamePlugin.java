/**
 * Copyright 2015-2016 the original author or authors.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.shenjia.mybatis.util.Strings;

/**
 * 
 * @author json
 *
 */
public class DynamicTableNamePlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable table) {
		List<Method> methods = new ArrayList<>();
		for (Method method : interfaze.getMethods()) {
			if ("updateByPrimaryKey".equals(method.getName())) {
				methods.add(createOverloadMethod(method, table));
				rewriteOriginalMethod(method, "update\\(", "update(tableName, ");
			} else if ("selectPage".equals(method.getName())) {
				methods.add(createOverloadMethod(method, table));
				rewriteOriginalMethod(method, table);
			} else if ("selectRange".equals(method.getName())) {
				methods.add(createOverloadMethod(method, table));
				rewriteOriginalMethod(method, table);
			}
			methods.add(method);
		}
		interfaze.getMethods().clear();
		interfaze.getMethods().addAll(methods);
		FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(table.getBaseRecordType());
		interfaze.addImportedType(recordType);
		return true;
	}

	@Override
	public boolean clientGeneralDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
	    IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, "delete\\(", "delete(tableName, ");
		return true;
	}

	@Override
	public boolean clientGeneralCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
	    IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientGeneralSelectMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientGeneralSelectDistinctMethodGenerated(Method method, Interface interfaze,
	    IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}

	@Override
	public boolean clientGeneralUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, table);
		return true;
	}
	
	@Override
	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze,
	    IntrospectedTable table) {
		addOverloadMethod(method, interfaze, table);
		rewriteOriginalMethod(method, "update\\(", "update(tableName, ");
		return true;
	}

	private void addOverloadMethod(Method method, Interface interfaze, IntrospectedTable table) {
		interfaze.addMethod(createOverloadMethod(method, table));
	}

	private Method createOverloadMethod(Method method, IntrospectedTable table) {
		Method overload = new Method(method.getName());
		overload.setDefault(method.isDefault());
		context.getCommentGenerator().addGeneralMethodAnnotation(overload, table, new HashSet<>());
		StringBuilder bodyBuf = new StringBuilder(100);
		method.getReturnType().ifPresent(rt -> {
			overload.setReturnType(rt);
			bodyBuf.append("return ");
		});
		bodyBuf.append(method.getName()).append("(null");
		for (Parameter p : method.getParameters()) {
			overload.addParameter(p);
			bodyBuf.append(", ").append(p.getName());
		}
		bodyBuf.append(");");
		overload.addBodyLine(bodyBuf.toString());
		return overload;
	}

	private void rewriteOriginalMethod(Method method, IntrospectedTable table) {
		String tableFiledName = JavaBeansUtil
		    .getValidPropertyName(table.getFullyQualifiedTable().getDomainObjectName());
		String replacement = Strings.join("null == tableName ? ", tableFiledName, " : ", tableFiledName,
		    ".withName(tableName)");
		rewriteOriginalMethod(method, tableFiledName, replacement);
	}

	private void rewriteOriginalMethod(Method method, String regex, String replacement) {
		method.addParameter(0, new Parameter(new FullyQualifiedJavaType("String"), "tableName"));
//		String firstLine = method.getBodyLines().get(0).replaceFirst(regex, replacement);
//		method.getBodyLines().remove(0);
//		method.addBodyLine(0, firstLine);
		for (int i = 0; i < method.getBodyLines().size(); i++) {
			String line = method.getBodyLines().get(i);
			String replaced = line.replaceFirst(regex, replacement);
			if (!line.equals(replaced)) {
				method.getBodyLines().remove(i);
				method.addBodyLine(i, replaced);
				return;
			}
		}
	}
}
