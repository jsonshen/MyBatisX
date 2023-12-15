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
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;

public class RewriteDynamicSqlSupportPlugin extends MyBatisXPlugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean dynamicSqlSupportGenerated(TopLevelClass clazz, IntrospectedTable table) {
		clazz.addImportedType(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.AliasableSqlTable"));
		
		FullyQualifiedJavaType clsType = new FullyQualifiedJavaType(table.getMyBatisDynamicSqlSupportType());
		String clsName = clsType.getShortName();
		String instName = JavaBeansUtil.getValidPropertyName(table.getMyBatisDynamicSQLTableObjectName());
		String tblName = StringUtility.escapeStringForJava(table.getFullyQualifiedTableNameAtRuntime());
		clazz.setSuperClass("AliasableSqlTable<" + clsName + ">");

		Method ctorMethod = new Method(clsName);
		ctorMethod.setVisibility(JavaVisibility.PRIVATE);
		ctorMethod.setConstructor(true);
		ctorMethod.addBodyLine("super(\"" + tblName + "\", " + clsName + "::new" + ");");
		clazz.addMethod(ctorMethod);

		clazz.getFields().clear();
		Field instField = new Field(instName, clsType);
		instField.addAnnotation("@Generated(\"org.mybatis.generator.api.MyBatisGenerator\")");
		instField.setVisibility(JavaVisibility.PUBLIC);
		instField.setStatic(true);
		instField.setFinal(true);
		instField.setInitializationString("new " + clsName + "()");
		clazz.addField(instField);

		InnerClass innerCls = clazz.getInnerClasses().get(0);
		innerCls.getFields().forEach(field -> {
			field.setStatic(true);
			field.addAnnotation("@Generated(\"org.mybatis.generator.api.MyBatisGenerator\")");
			field.getInitializationString().ifPresent(str -> {
				str = str.replaceAll("column\\(", "SqlColumn.of(").replaceFirst("\\, ", ", " + instName + ", ");
				field.setInitializationString(str);
			});
			clazz.addField(field);
		});
		clazz.getInnerClasses().clear();
		return true;
	}
}
