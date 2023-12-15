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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerInterface;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;

public class DeleteGeneratedAnnotationPlugin extends MyBatisXPlugin {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable table) {
		refillImportedTypes(interfaze.getImportedTypes());
		refillInterfaceGeneratedAnnotation(interfaze);
		for (InnerClass cls : interfaze.getInnerClasses()) {
			refillClassGeneratedAnnotation(cls);
		}
		for (InnerInterface iface : interfaze.getInnerInterfaces()) {
			refillInterfaceGeneratedAnnotation(iface);
		}
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass clazz, IntrospectedTable table) {
		refillImportedTypes(clazz.getImportedTypes());
		refillClassGeneratedAnnotation(clazz);
		for (InnerClass cls : clazz.getInnerClasses()) {
			refillClassGeneratedAnnotation(cls);
		}
		for (InnerInterface iface : clazz.getInnerInterfaces()) {
			refillInterfaceGeneratedAnnotation(iface);
		}
		return true;
	}

	@Override
	public boolean dynamicSqlSupportGenerated(TopLevelClass clazz, IntrospectedTable table) {
		refillImportedTypes(clazz.getImportedTypes());
		refillClassGeneratedAnnotation(clazz);
		for (InnerClass cls : clazz.getInnerClasses()) {
			refillClassGeneratedAnnotation(cls);
		}
		for (InnerInterface iface : clazz.getInnerInterfaces()) {
			refillInterfaceGeneratedAnnotation(iface);
		}
		return true;
	}
	
	private void refillInterfaceGeneratedAnnotation(InnerInterface interfaze) {
		refillAnnotations(interfaze.getAnnotations());

		for (Field field : interfaze.getFields()) {
			refillAnnotations(field.getAnnotations());
		}

		for (Method method : interfaze.getMethods()) {
			refillAnnotations(method.getAnnotations());
		}
	}

	private void refillClassGeneratedAnnotation(InnerClass clazz) {
		refillAnnotations(clazz.getAnnotations());

		for (Field field : clazz.getFields()) {
			refillAnnotations(field.getAnnotations());
		}

		for (Method method : clazz.getMethods()) {
			refillAnnotations(method.getAnnotations());
		}
	}

	private void refillAnnotations(List<String> annotations) {
		if (null == annotations || annotations.isEmpty()) {
			return;
		}
		List<String> temp = new ArrayList<>(annotations.size() - 1);
		for (String anno : annotations) {
			if (!anno.startsWith("@Generated(")) {
				temp.add(anno);
			}
		}
		annotations.clear();
		annotations.addAll(temp);
	}
	
	private void refillImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
		if (null == importedTypes || importedTypes.isEmpty()) {
			return;
		}
		List<FullyQualifiedJavaType> temp = new ArrayList<>();
		for (FullyQualifiedJavaType type : importedTypes) {
			if (!"jakarta.annotation.Generated".equals(type.getFullyQualifiedName())) {
				temp.add(type);
			}
		}
		importedTypes.clear();
		importedTypes.addAll(temp);
	}
}
