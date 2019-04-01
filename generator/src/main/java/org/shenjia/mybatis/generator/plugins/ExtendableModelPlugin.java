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

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 
 * @author json
 *
 */
public class ExtendableModelPlugin extends PluginAdapter {

	private List<GeneratedJavaFile> extendableModels = new ArrayList<GeneratedJavaFile>();
	private boolean generateBuildMethod = true;
	private boolean generateSerialVersionUID = true;

	public boolean validate(List<String> warnings) {
		if (StringUtility.stringHasValue(properties.getProperty("generateBuildMethod"))) {
			generateBuildMethod = Boolean.parseBoolean(properties.getProperty("generateBuildMethod"));
		}
		if (StringUtility.stringHasValue(properties.getProperty("generateSerialVersionUID"))) {
			generateSerialVersionUID = Boolean.parseBoolean(properties.getProperty("generateSerialVersionUID"));
		}
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		return extendableModels;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass oldModelClass, IntrospectedTable introspectedTable) {
		// New Model Class
		TopLevelClass newModelClass = new TopLevelClass(oldModelClass.getType().getFullyQualifiedName());
		
		// Old Model Class
		oldModelClass.setAbstract(true);
		FullyQualifiedJavaType oldModelType = oldModelClass.getType();
		Class<?> clazz = oldModelType.getClass();
		try {
			// Modify old model class package name
			Field pn = clazz.getDeclaredField("packageName");
			pn.setAccessible(true);
			String packageName = oldModelType.getPackageName() + ".internal";
			pn.set(oldModelType, packageName);
			
			// Modify old model class base short name
			Field bsn = clazz.getDeclaredField("baseShortName");
			bsn.setAccessible(true);
			String shortName = "Abstract" + oldModelType.getShortName();
			bsn.set(oldModelType, shortName);

			// Modify old model class base qualified name
			Field bqn = clazz.getDeclaredField("baseQualifiedName");
			bqn.setAccessible(true);
			String fullName = oldModelType.getFullyQualifiedNameWithoutTypeParameters();
			fullName = oldModelType.getPackageName() + "." + shortName;
			bqn.set(oldModelType, fullName);
			
			if (generateBuildMethod) {
				// Add old model class build method
				Method buildMethod = new Method();
			    buildMethod.setVisibility(JavaVisibility.PUBLIC);
			    buildMethod.setReturnType(new FullyQualifiedJavaType("<T extends " + shortName + ">"));
			    // MPG API BUG
			    buildMethod.setName("T build");
			    buildMethod.addBodyLine("return (T)this;");
			    buildMethod.addAnnotation("@SuppressWarnings(\"unchecked\")");
			    oldModelClass.addMethod(buildMethod);
			    context.getCommentGenerator().addGeneralMethodComment(buildMethod, introspectedTable);
			}

			List<Method> oldModelMethods = oldModelClass.getMethods();
			for (Method oldModelMethod : oldModelMethods) {
				if (!oldModelMethod.isConstructor()) {
					continue;
				}
				Method newModelMethod = new Method();
				newModelMethod.setConstructor(true);
				newModelMethod.setVisibility(JavaVisibility.PUBLIC);
				newModelMethod.setName(oldModelMethod.getName());
				StringBuilder bodyBuf = new StringBuilder("super(");
				for (Parameter p : oldModelMethod.getParameters()) {
					newModelMethod.addParameter(p);
					bodyBuf.append(p.getName() + " ,");
				}
				int bodyLen = bodyBuf.length();
				if (bodyLen > 6) {
					bodyBuf.delete(bodyLen - 2, bodyLen);
				}
				bodyBuf.append(");");
				newModelMethod.addBodyLine(bodyBuf.toString());
			    newModelClass.addMethod(newModelMethod);
			    
			    // Modify old model constructor method name.
			    oldModelMethod.setName("Abstract" + oldModelMethod.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// New Model Class
		if (generateSerialVersionUID) {
			newModelClass.addField(buildSerialVersionUID(introspectedTable));
		}
		newModelClass.setVisibility(JavaVisibility.PUBLIC);
		newModelClass.setSuperClass(oldModelType);
		newModelClass.addImportedType(oldModelType);

		// Generate java source file
		String targetProject = context.getJavaModelGeneratorConfiguration().getTargetProject();
		if (!checkExists(targetProject, newModelClass)) {
			GeneratedJavaFile modelJavaFile = new GeneratedJavaFile(newModelClass, targetProject, context.getJavaFormatter());
			extendableModels.add(modelJavaFile);
		} else {
			System.out.println("---- JAVA FILE EXISTS, IGNORED. --:" + newModelClass.getType().getFullyQualifiedName());
		}
		return true;
	}
	
	/**
	 * Build serial version UID
	 * @param it
	 * @return
	 */
	private org.mybatis.generator.api.dom.java.Field buildSerialVersionUID(IntrospectedTable it) {
		org.mybatis.generator.api.dom.java.Field field = new org.mybatis.generator.api.dom.java.Field();
		field.setName("serialVersionUID");
		field.setType(new FullyQualifiedJavaType("long"));
		field.setFinal(true);
		field.setStatic(true);
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setInitializationString(new Random().nextLong() + "L");
		context.getCommentGenerator().addFieldComment(field, it);
		return field;
	}
	
	private boolean checkExists(String targetProject, TopLevelClass newModelClass) {
		String fullName = newModelClass.getType().getFullyQualifiedNameWithoutTypeParameters();
		File pomFile = new File(System.getProperty("user.dir") + "/pom.xml");
		String filePath = System.getProperty("user.dir") + "/";
		if (pomFile.exists()) {
			 filePath += targetProject + "/" + fullName.replaceAll("\\.", "/") + ".java";
		}
		File javaFile = new File(filePath);
		return javaFile.exists();
	}
}
