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

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;

/**
 * 
 * @author json
 *
 */
public class RenameExamplePlugin extends MyBatisXPlugin {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		if (null != introspectedTable.getExampleType()) {
			renameExampleClass(introspectedTable);
		}
	}

	@Override
	public boolean clientGenerated(Interface interfaze,
			IntrospectedTable introspectedTable) {
		for (Method m : interfaze.getMethods()) {
			if (m.getName().indexOf("Example") < 0) {
				continue;
			}
			renameExampleMethod(m);
		}
		return true;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		renameXmlNodes(sqlMap);
		return true;
	}
	
	private void renameExampleClass(IntrospectedTable it) {
		String oldType = it.getExampleType();
		String newType = oldType.replaceAll("Example", "Filter"); //.replaceAll("domain", "filter");
		it.setExampleType(newType);
	}

	private void renameExampleMethod(Method m) {
		m.setName(m.getName().replaceAll("Example", "Filter"));
		List<Parameter> params = m.getParameters();
		for (int i = 0; i < params.size(); i++) {
			Parameter oldParam = params.get(i);
			if (!"example".equals(oldParam.getName())) {
				continue;
			}
			Parameter newParam = new Parameter(oldParam.getType(), "filter");
			for (String a : oldParam.getAnnotations()) {
				newParam.addAnnotation(a.replaceAll("example", "filter"));
			}
			params.set(i, newParam);
		}
	}

	private void renameXmlNodes(GeneratedXmlFile sqlMap) {
		try {
			Field field = sqlMap.getClass().getDeclaredField("document");
			field.setAccessible(true);
			Document doc = (Document) field.get(sqlMap);
			XmlElement oldRoot = doc.getRootElement();
			XmlElement newRoot = new XmlElement(oldRoot.getName());
			updateNode(oldRoot, newRoot);
			doc.setRootElement(newRoot);
			field.set(sqlMap, doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateNode(XmlElement oldRoot, XmlElement newRoot) {
		for (Attribute a : oldRoot.getAttributes()) {
			String name = a.getName();
			String value = a.getValue();
			if ("id".equals(name) || "refid".equals(name)) {
				value = value.replaceAll("Example", "Filter");
			} else if ("collection".equals(a.getName())) {
				value = value.replaceAll("example.oredCriteria", "filter.oredCriteria");
			}
			newRoot.addAttribute(new Attribute(name, value));
		}
		List<VisitableElement> oldElements = oldRoot.getElements();
		for (VisitableElement e : oldElements) {
			if (e instanceof TextElement) {
				newRoot.addElement(e);
				continue;
			}
			XmlElement oldNode = (XmlElement) e;
			XmlElement newNode = new XmlElement(oldNode.getName());
			newRoot.addElement(newNode);
			updateNode(oldNode, newNode);
		}
	}
}
