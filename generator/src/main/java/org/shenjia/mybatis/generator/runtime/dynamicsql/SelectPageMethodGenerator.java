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
package org.shenjia.mybatis.generator.runtime.dynamicsql;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class SelectPageMethodGenerator extends AbstractMethodGenerator {

	private FullyQualifiedJavaType recordType;

	private SelectPageMethodGenerator(Builder builder) {
		super(builder);
		recordType = builder.recordType;
	}

	@Override
	public MethodAndImports generateMethodAndImports() {
		if (!introspectedTable.getRules().generateSelectByExampleWithBLOBs()
		    && !introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
			return null;
		}

		Set<FullyQualifiedJavaType> imports = new HashSet<FullyQualifiedJavaType>();
		imports.add(new FullyQualifiedJavaType("java.util.Optional"));
		imports.add(new FullyQualifiedJavaType("java.util.function.Function"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.QueryExpressionDSL"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SortSpecification"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSL"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectModel"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.WhereApplier"));
		imports.add(new FullyQualifiedJavaType("org.shenjia.mybatis.paging.PageAdapter"));
		imports.add(new FullyQualifiedJavaType("org.shenjia.mybatis.paging.Page"));
		imports.add(FullyQualifiedJavaType.getNewListInstance());
		imports.add(recordType);

		Method method = new Method("selectPage");
		context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
		method.setDefault(true);
		method.setReturnType(new FullyQualifiedJavaType("Page<" + recordType.getShortNameWithoutTypeArguments() + ">"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("long"), "currentPage"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "pageSize"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("WhereApplier"), "where"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("SortSpecification..."), "sorts"));

		StringBuilder buf = new StringBuilder();
		buf.append("Function<SelectModel, PageAdapter<");
		buf.append(recordType.getShortNameWithoutTypeArguments());
		buf.append(">> adapter = model -> PageAdapter.of(model, this::count, this::selectMany, currentPage, pageSize);");
		method.addBodyLine(buf.toString());
		
		buf = new StringBuilder();
		buf.append("QueryExpressionDSL<PageAdapter<");
		buf.append(recordType.getShortNameWithoutTypeArguments());
		buf.append(">> dsl = SelectDSL.select(adapter, selectList)");
		method.addBodyLine(buf.toString());
		
		method.addBodyLine("    .from(" + tableFieldName + ");");
		method.addBodyLine("Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));");
		method.addBodyLine("Optional.ofNullable(sorts).filter(ss -> ss.length > 0).ifPresent(ss -> dsl.orderBy(ss));");
		method.addBodyLine("return dsl.build().execute();");

		return MethodAndImports.withMethod(method).withImports(imports).build();
	}

	@Override
	public boolean callPlugins(Method method, Interface interfaze) {
		return true;
	}

	public static class Builder extends BaseBuilder<Builder> {

		private FullyQualifiedJavaType recordType;

		public Builder withRecordType(FullyQualifiedJavaType recordType) {
			this.recordType = recordType;
			return this;
		}

		@Override
		public Builder getThis() {
			return this;
		}

		public SelectPageMethodGenerator build() {
			return new SelectPageMethodGenerator(this);
		}
	}

}
