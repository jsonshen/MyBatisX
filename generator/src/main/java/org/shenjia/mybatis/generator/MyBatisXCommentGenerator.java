package org.shenjia.mybatis.generator;

import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class MyBatisXCommentGenerator extends DefaultCommentGenerator {

	private boolean suppressAllGeneratedAnnotation = false;

	@Override
	public void addConfigurationProperties(Properties properties) {
		super.addConfigurationProperties(properties);
		suppressAllGeneratedAnnotation = StringUtility.isTrue(properties.getProperty("suppressAllGeneratedAnnotation"));
	}

	@Override
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		if (!suppressAllGeneratedAnnotation) {
			super.addClassAnnotation(innerClass, introspectedTable, imports);
		}
	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		if (!suppressAllGeneratedAnnotation) {
			super.addGeneralMethodAnnotation(method, introspectedTable, imports);
		}
	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
		if (!suppressAllGeneratedAnnotation) {
			super.addGeneralMethodAnnotation(method, introspectedTable, introspectedColumn, imports);
		}
	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		if (!suppressAllGeneratedAnnotation) {
			super.addFieldAnnotation(field, introspectedTable, imports);
		}
	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
		if (!suppressAllGeneratedAnnotation) {
			super.addFieldAnnotation(field, introspectedTable, introspectedColumn, imports);
		}
	}

}
