/*
 *    Copyright 2006-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.shenjia.mybatis.generator.springjdbc;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getEscapedColumnName;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;
import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * This model generator builds a flat model with default constructor and
 * getters/setters. It does not support the immutable model, or constructor
 * based attributes.
 *
 * @author Jeff Butler
 */
public class SpringJdbcDynamicSqlModelGenerator extends AbstractJavaGenerator {

	public SpringJdbcDynamicSqlModelGenerator(String project) {
		super(project);
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(getString("Progress.8", table.toString()));
		Plugin plugins = context.getPlugins();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		TopLevelClass modelClass = new TopLevelClass(type);
		modelClass.setVisibility(JavaVisibility.PUBLIC);
		modelClass.addFileCommentLine("// @formatter:off");

		FullyQualifiedJavaType superClass = getSuperClass();
		if (superClass != null) {
			modelClass.setSuperClass(superClass);
			modelClass.addImportedType(superClass);
		}

		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(modelClass);
		commentGenerator.addModelClassComment(modelClass, introspectedTable);

		if (introspectedTable.isConstructorBased()) {
			addParameterizedConstructor(modelClass);
			if (!introspectedTable.isImmutable()) {
				addDefaultConstructorWithGeneratedAnnotatoin(modelClass);
			}
		}

		String rootClass = getRootClass();
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (RootClassInfo.getInstance(rootClass, warnings).containsProperty(col)) {
				continue;
			}

			Field field = getJavaBeansField(col, context, introspectedTable);
			if (plugins.modelFieldGenerated(field, modelClass, col, introspectedTable,
			    Plugin.ModelClassType.BASE_RECORD)) {
				modelClass.addField(field);
				modelClass.addImportedType(field.getType());
			}

			Method method = getJavaBeansGetter(col, context, introspectedTable);
			if (plugins.modelGetterMethodGenerated(method, modelClass, col, introspectedTable,
			    Plugin.ModelClassType.BASE_RECORD)) {
				modelClass.addMethod(method);
			}

			if (!introspectedTable.isImmutable()) {
				method = getJavaBeansSetter(col, context, introspectedTable);
				if (plugins.modelSetterMethodGenerated(method, modelClass, col, introspectedTable,
				    Plugin.ModelClassType.BASE_RECORD)) {
					modelClass.addMethod(method);
				}
			}
		}

		addJdbcModelInterface(modelClass);
		addTableClass(modelClass);

		List<CompilationUnit> answer = new ArrayList<>();
		if (context.getPlugins().modelBaseRecordClassGenerated(modelClass, introspectedTable)) {
			answer.add(modelClass);
		}
		return answer;
	}

	private FullyQualifiedJavaType getSuperClass() {
		FullyQualifiedJavaType superClass;
		String rootClass = getRootClass();
		if (rootClass != null) {
			superClass = new FullyQualifiedJavaType(rootClass);
		} else {
			superClass = null;
		}

		return superClass;
	}

	private void addParameterizedConstructor(TopLevelClass topLevelClass) {
		Method method = new Method(topLevelClass.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		context.getCommentGenerator()
		    .addGeneralMethodAnnotation(method, introspectedTable, topLevelClass.getImportedTypes());

		List<IntrospectedColumn> constructorColumns = introspectedTable.getAllColumns();

		for (IntrospectedColumn col : constructorColumns) {
			method.addParameter(new Parameter(col.getFullyQualifiedJavaType(), col.getJavaProperty()));
		}

		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(col.getJavaProperty());
			sb.append(" = "); //$NON-NLS-1$
			sb.append(col.getJavaProperty());
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		topLevelClass.addMethod(method);
	}

	private void addJdbcModelInterface(TopLevelClass modelClass) {
		modelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.jdbc.core.RowMapper"));
		modelClass.addImportedType(new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcModel"));
		modelClass
		    .addSuperInterface(new FullyQualifiedJavaType("JdbcModel<" + modelClass.getType().getShortName() + ">"));

		Field tableField = new Field("TABLE", new FullyQualifiedJavaType("Table"));
		tableField.addJavaDocLine("");
		tableField.setFinal(true);
		tableField.setStatic(true);
		tableField.setVisibility(JavaVisibility.PUBLIC);
		tableField.setInitializationString("new Table()");
		modelClass.getFields().add(0, tableField);

		addRowMapperMethod(modelClass);
		addTableMethod(modelClass);
		addColumnsMethod(modelClass);
	}

	private void addRowMapperMethod(TopLevelClass modelClass) {
		String shortName = modelClass.getType().getShortName();
		Method method = new Method("rowMapper");
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("RowMapper<" + shortName + ">"));
		method.addBodyLine("return (rs, rowNum) -> {");
		method.addBodyLine(shortName + " record = new " + shortName + "();");
		StringBuilder buf = new StringBuilder();
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			buf.setLength(0);
			buf.append("record."); //$NON-NLS-1$
			buf.append(JavaBeansUtil.getSetterMethodName(col.getJavaProperty()));
			FullyQualifiedJavaType javaType = col.getFullyQualifiedJavaType();
			if (javaType.equals(FullyQualifiedJavaType.getStringInstance())) {
				buf.append("(rs.getString(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getIntegerInstance())) {
				buf.append("(rs.getInt(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getLongInstance())) {
				buf.append("(rs.getLong(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getShortInstance())) {
				buf.append("(rs.getShort(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getFloatInstance())) {
				buf.append("(rs.getFloat(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getBooleanInstance())) {
				buf.append("(rs.getBoolean(\""); //$NON-NLS-1$
			} else if (javaType.equals(PrimitiveTypeWrapper.getDateInstance())) {
				buf.append("(rs.getDate(\""); //$NON-NLS-1$
			} else {
				buf.append("(rs.getObject(\""); //$NON-NLS-1$
			}
			buf.append(col.getActualColumnName());
			buf.append("\"));");
			method.addBodyLine(buf.toString());
		}
		method.addBodyLine("return record;");
		method.addBodyLine("};");

		modelClass.addMethod(method);
	}

	private void addTableMethod(TopLevelClass modelClass) {
		Method method = new Method("table");
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("Table"));
		method.addBodyLine("return TABLE;");
		modelClass.addMethod(method);
	}

	private void addColumnsMethod(TopLevelClass modelClass) {
		modelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		modelClass.addImportedType(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlColumn"));
		Method method = new Method("columns");
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("List<SqlColumn<?>>"));
		method.addBodyLine("return TABLE.columns;");
		modelClass.addMethod(method);
	}

	private void addTableClass(TopLevelClass modelClass) {
		modelClass.addImportedType(new FullyQualifiedJavaType("java.sql.JDBCType"));
		modelClass.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
		modelClass.addImportedType(new FullyQualifiedJavaType("java.util.Collections"));
		modelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		modelClass.addImportedType(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.AliasableSqlTable"));
		modelClass.addImportedType(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlColumn"));
		InnerClass tableClass = new InnerClass(new FullyQualifiedJavaType("Table"));
		modelClass.addInnerClass(tableClass);

		tableClass.setVisibility(JavaVisibility.PUBLIC);
		tableClass.setStatic(true);
		tableClass.setSuperClass(new FullyQualifiedJavaType("AliasableSqlTable<Table>"));

		Field columnsField = new Field("columns", new FullyQualifiedJavaType("List<SqlColumn<?>>"));
		columnsField.addJavaDocLine("");
		columnsField.setFinal(true);
		columnsField.setVisibility(JavaVisibility.PUBLIC);
		tableClass.addField(columnsField);

		Method constructor = new Method("Table");
		constructor.setConstructor(true);
		constructor.setVisibility(JavaVisibility.PUBLIC);
		constructor.addBodyLine("super(\""
		    + escapeStringForJava(introspectedTable.getFullyQualifiedTableNameAtRuntime()) + "\", Table::new);");
		constructor.addBodyLine("List<SqlColumn<?>> list = new ArrayList<>();");

		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		for (IntrospectedColumn column : columns) {
			addTableField(modelClass, tableClass, column);
			constructor.addBodyLine("list.add(" + column.getJavaProperty() + ");");
		}

		constructor.addBodyLine("this.columns = Collections.unmodifiableList(list);");
		tableClass.addMethod(constructor);
	}

	private void addTableField(TopLevelClass modelClass, InnerClass tableClass, IntrospectedColumn column) {
		modelClass.addImportedType(column.getFullyQualifiedJavaType());
		FullyQualifiedJavaType javaType;
		if (column.getFullyQualifiedJavaType().isPrimitive()) {
			javaType = column.getFullyQualifiedJavaType().getPrimitiveTypeWrapper();
		} else {
			javaType = column.getFullyQualifiedJavaType();
		}
		Field field = new Field(column.getJavaProperty(), calcTableFieldType(javaType));
		field.setVisibility(JavaVisibility.PUBLIC);
		field.setFinal(true);
		field.setInitializationString(calcTableFieldInitStr(column, javaType));
		tableClass.addField(field);
	}

	private FullyQualifiedJavaType calcTableFieldType(FullyQualifiedJavaType javaType) {
		return new FullyQualifiedJavaType(String.format("SqlColumn<%s>", javaType.getShortName()));
	}

	private String calcTableFieldInitStr(IntrospectedColumn column, FullyQualifiedJavaType javaType) {
		StringBuilder initStr = new StringBuilder();
		initStr.append(String.format("column(\"%s\", JDBCType.%s", escapeStringForJava(getEscapedColumnName(column)),
		    column.getJdbcTypeName()));
		if (stringHasValue(column.getTypeHandler())) {
			initStr.append(String.format(", \"%s\")", column.getTypeHandler()));
		} else {
			initStr.append(')');
		}
		if (isTrue(column.getProperties().getProperty(PropertyRegistry.COLUMN_OVERRIDE_FORCE_JAVA_TYPE))) {
			initStr.append(".withJavaType(");
			initStr.append(javaType.getShortName());
			initStr.append(".class)");
		}
		return initStr.toString();
	}
}
