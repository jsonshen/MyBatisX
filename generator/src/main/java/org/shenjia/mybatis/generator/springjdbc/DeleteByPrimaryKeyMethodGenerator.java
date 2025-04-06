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

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class DeleteByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {

	private final FragmentGenerator fragmentGenerator;

	private DeleteByPrimaryKeyMethodGenerator(Builder builder) {
		super(builder);
		fragmentGenerator = builder.fragmentGenerator;
	}

	@Override
	public MethodsAndImports generateMethodAndImports() {
		if (!Utils.generateDeleteByPrimaryKey(introspectedTable)) {
			return null;
		}

		Set<String> staticImports = new HashSet<>();
		Set<FullyQualifiedJavaType> imports = new HashSet<>();
		Set<Method> methods = new HashSet<>();
		
		staticImports.add("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");
		staticImports.add(introspectedTable.getBaseRecordType() + "." + fragmentGenerator.getTableFieldName());
		
		MethodParts methodParts = fragmentGenerator.getPrimaryKeyWhereClauseAndParameters();
		imports.addAll(methodParts.getImports());
		
		Method method1 = new Method("deleteByPrimaryKey");
		method1.setDefault(true);
		method1.setReturnType(FullyQualifiedJavaType.getIntInstance());
		for (Parameter parameter : methodParts.getParameters()) {
			method1.addParameter(parameter);
		}
		method1.addBodyLine("return delete(c -> ");
		method1.addBodyLines(methodParts.getBodyLines());
		methods.add(method1);

		Method method2 = new Method("deleteByPrimaryKey");
		method2.setDefault(true);
		method2.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method2.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "tableName"));
		for (Parameter parameter : methodParts.getParameters()) {
			method2.addParameter(parameter);
		}
		method2.addBodyLine("return delete(tableName, c -> ");
		method2.addBodyLines(methodParts.getBodyLines());
		methods.add(method2);

		return MethodsAndImports.withMethods(methods).withImports(imports).withStaticImports(staticImports).build();
	}

	@Override
	public boolean callPlugins(Method method, Interface interfaze) {
		return context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
	}

	public static class Builder extends BaseBuilder<Builder> {

		private FragmentGenerator fragmentGenerator;

		public Builder withFragmentGenerator(FragmentGenerator fragmentGenerator) {
			this.fragmentGenerator = fragmentGenerator;
			return this;
		}

		@Override
		public Builder getThis() {
			return this;
		}

		public DeleteByPrimaryKeyMethodGenerator build() {
			return new DeleteByPrimaryKeyMethodGenerator(this);
		}
	}
}
