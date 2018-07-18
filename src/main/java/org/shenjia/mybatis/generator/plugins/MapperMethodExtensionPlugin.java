/**
 * Copyright 2017-2018 the original author or authors.
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
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;
import org.shenjia.mybatis.generator.runtime.dynamic.sql.elements.SelectOneByExampleMethodGenerator;
import org.shenjia.mybatis.generator.runtime.dynamic.sql.elements.SelectPageByExampleMethodGenerator;

/**
 * 
 * @author json
 *
 */
public class MapperMethodExtensionPlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		String resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result"; //$NON-NLS-1$
		String tableFieldName = JavaBeansUtil
				.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
		FragmentGenerator fragmentGenerator = new FragmentGenerator.Builder().withIntrospectedTable(introspectedTable)
				.withResultMapId(resultMapId).build();

		String addSelectPageByExampleMethod = properties.getProperty("addSelectPageByExampleMethod", "true");
		if (StringUtility.isTrue(addSelectPageByExampleMethod)) {
			addSelectPageByExampleMethod(interfaze, introspectedTable, fragmentGenerator, tableFieldName, recordType);
		}
		String addSelectOneByExampleMethod = properties.getProperty("addSelectOneByExampleMethod", "true");
		if (StringUtility.isTrue(addSelectOneByExampleMethod)) {
			addSelectOneByExampleMethod(interfaze, introspectedTable, fragmentGenerator, tableFieldName, recordType);
		}
		return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
	}

	private void addSelectPageByExampleMethod(Interface interfaze, IntrospectedTable introspectedTable,
			FragmentGenerator fragmentGenerator, String tableFieldName, FullyQualifiedJavaType recordType) {
		SelectPageByExampleMethodGenerator generator = new SelectPageByExampleMethodGenerator.Builder()
				.withContext(context).withFragmentGenerator(fragmentGenerator).withIntrospectedTable(introspectedTable)
				.withTableFieldName(tableFieldName).withRecordType(recordType).build();
		generate(interfaze, generator);
	}

	private void addSelectOneByExampleMethod(Interface interfaze, IntrospectedTable introspectedTable,
			FragmentGenerator fragmentGenerator, String tableFieldName, FullyQualifiedJavaType recordType) {
		SelectOneByExampleMethodGenerator generator = new SelectOneByExampleMethodGenerator.Builder()
				.withContext(context).withFragmentGenerator(fragmentGenerator).withIntrospectedTable(introspectedTable)
				.withTableFieldName(tableFieldName).withRecordType(recordType).build();
		generate(interfaze, generator);
	}

	private void generate(Interface interfaze, AbstractMethodGenerator generator) {
		MethodAndImports mi = generator.generateMethodAndImports();
		if (mi != null && generator.callPlugins(mi.getMethod(), interfaze)) {
			interfaze.addMethod(mi.getMethod());
			interfaze.addImportedTypes(mi.getImports());
			interfaze.addStaticImports(mi.getStaticImports());
		}
	}
}
