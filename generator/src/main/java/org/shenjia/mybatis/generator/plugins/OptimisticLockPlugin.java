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

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 
 * @author json
 *
 */
public class OptimisticLockPlugin extends PluginAdapter {

	private String lockColumnName = "version";

	public boolean validate(List<String> warnings) {
		if (StringUtility.stringHasValue(properties.getProperty("lockColumnName"))) {
			this.lockColumnName = properties.getProperty("lockColumnName");
		}
		return true;
	}
	
	@Override
	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return sqlMapUpdateByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}
	
	@Override
	public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		IntrospectedColumn ic = introspectedTable.getColumn(lockColumnName);
		if (null == ic) {
			String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
			System.err.println("[" + tableName + "] lock column not exists");
			return true;
		}
		String jp = ic.getJavaProperty();
		List<Element> elements = element.getElements();
		for (int i = 0; i < elements.size(); i++) {
			String content = ((TextElement) elements.get(i)).getContent();
			if (content.indexOf(lockColumnName) > 0) {
				elements.set(i, new TextElement(content.replaceAll(jp, jp + " + 1")));
				break;
			}
		}
		return true;
	}
	
	@Override
	public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		try {
			IntrospectedColumn ic = introspectedTable.getColumn(lockColumnName);
			if (null == ic) {
				String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
				System.err.println("[" + tableName + "] lock column not exists");
				return true;
			}

			List<Element> elements = element.getElements();
			for (int i = 0; i < elements.size(); i++) {
				Element oldNode = elements.get(i);
				if (updateSetNode(oldNode, ic)) {
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		IntrospectedColumn ic = introspectedTable.getColumn(lockColumnName);
		if (null == ic) {
			String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
			System.err.println("[" + tableName + "] lock column not exists");
			return true;
		}
		String jp = ic.getJavaProperty();
		List<Element> elements = element.getElements();
		for (int i = 0; i < elements.size(); i++) {
			String content = ((TextElement) elements.get(i)).getContent();
			if (content.indexOf(lockColumnName) > 0) {
				elements.set(i, new TextElement(content.replaceAll(jp, jp + " + 1")));
				continue;
			}
			if (content.indexOf("where") >= 0) {
				elements.set(i, buildWhereElement(content, jp, ic.getJdbcTypeName()));
				break;
			}
		}
		return true;
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		try {
			IntrospectedColumn ic = introspectedTable.getColumn(lockColumnName);
			if (null == ic) {
				String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
				System.err.println("[" + tableName + "] lock column not exists");
				return true;
			}

			List<Element> elements = element.getElements();
			for (int i = 0; i < elements.size(); i++) {
				Element oldNode = elements.get(i);
				updateSetNode(oldNode, ic);
				Element newNode = updateWhereNode(oldNode, ic);
				if (null != newNode) {
					elements.set(i, newNode);
					break;
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return true;
	}

	private boolean updateSetNode(Element element, IntrospectedColumn ic) {
		if (!(element instanceof XmlElement)) {
			return false;
		}
		XmlElement xe = (XmlElement) element;
		if (!"set".equals(xe.getName())) {
			return false;
		}
		for (Element e : xe.getElements()) {
			if (!(e instanceof XmlElement)) {
				continue;
			}
			XmlElement setNode = (XmlElement) e;
			for (Attribute a : setNode.getAttributes()) {
				if (!"test".equals(a.getName())) {
					continue;
				}
				String jp = ic.getJavaProperty();
				if (a.getValue().indexOf(jp) < 0) {
					continue;
				}
				List<Element> elements = setNode.getElements();
				String content = ((TextElement) elements.get(0)).getContent();
				elements.set(0, new TextElement(content.replaceAll(jp, jp + " + 1")));
				return true;
			}
		}
		return false;
	}

	private Element updateWhereNode(Element element, IntrospectedColumn ic) {
		if (!(element instanceof TextElement)) {
			return null;
		}
		String content = ((TextElement) element).getContent();
		if (content.indexOf("where ") < 0) {
			return null;
		}
		return buildWhereElement(content, ic.getJavaProperty(), ic.getJdbcTypeName());
	}
	
	private TextElement buildWhereElement(String content, String javaProperty, String javaTypeName) {
		int idx = content.indexOf("where") + 6;
		StringBuilder buf = new StringBuilder(200);
		buf.append(content.substring(0, idx)).append(lockColumnName).append(" = #{").append(javaProperty)
				.append(",jdbcType=").append(javaTypeName).append("} and ").append(content.substring(idx));
		return new TextElement(buf.toString());
	}
}
