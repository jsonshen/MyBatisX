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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;
import org.shenjia.mybatis.generator.api.MyBatisXPlugin;
import org.shenjia.mybatis.generator.runtime.dynamicsql.SelectPageMethodGenerator;
import org.shenjia.mybatis.generator.runtime.dynamicsql.SelectRangeMethodGenerator;

/**
 * Dynamic SQL extension plugin
 * 
 * @author Jason Shen
 *
 */
public class DynamicSqlExtensionPlugin extends MyBatisXPlugin {

    private static final Log LOG = LogFactory.getLog(DynamicSqlExtensionPlugin.class);
    private List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String suffix = properties.getProperty("dynamicSqlSupportSuffix");
        String type = introspectedTable.getMyBatisDynamicSqlSupportType();
        if (null != suffix && type.endsWith("DynamicSqlSupport")) {
            String newType = type.substring(0, type.length() - 17) + suffix;
            introspectedTable.setMyBatisDynamicSqlSupportType(newType);
        }
        super.initialized(introspectedTable);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return generatedJavaFiles;
    }

    @Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        String resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result"; //$NON-NLS-1$
        String tableFieldName = JavaBeansUtil.getValidPropertyName(introspectedTable.getFullyQualifiedTable()
            .getDomainObjectName());
        FragmentGenerator fragmentGenerator = new FragmentGenerator.Builder().withIntrospectedTable(introspectedTable)
            .withResultMapId(resultMapId)
            .build();
        // generateDaoClassIfNotExsits
        generateDaoClassIfNotExsits(interfaze, introspectedTable);
        // addSelectPageMethod
        if (StringUtility.isTrue(properties.getProperty("addSelectPageMethod", "true"))) {
            addSelectPageMethod(interfaze, introspectedTable, fragmentGenerator, tableFieldName, recordType);
        }
        if (StringUtility.isTrue(properties.getProperty("addSelectRangeMethod", "true"))) {
            addSelectRangeMethod(interfaze, introspectedTable, fragmentGenerator, tableFieldName, recordType);
        }
        return super.clientGenerated(interfaze, introspectedTable);
    }

    private void addSelectRangeMethod(Interface interfaze,
        IntrospectedTable introspectedTable,
        FragmentGenerator fragmentGenerator,
        String tableFieldName,
        FullyQualifiedJavaType recordType) {
        SelectRangeMethodGenerator generator = new SelectRangeMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    private void addSelectPageMethod(Interface interfaze,
        IntrospectedTable introspectedTable,
        FragmentGenerator fragmentGenerator,
        String tableFieldName,
        FullyQualifiedJavaType recordType) {
        SelectPageMethodGenerator generator = new SelectPageMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    private void generate(Interface interfaze,
        AbstractMethodGenerator generator) {
        MethodAndImports mi = generator.generateMethodAndImports();
        if (mi != null && generator.callPlugins(mi.getMethod(), interfaze)) {
            interfaze.addMethod(mi.getMethod());
            interfaze.addImportedTypes(mi.getImports());
            interfaze.addStaticImports(mi.getStaticImports());
        }
    }

    private void generateDaoClassIfNotExsits(Interface mapperInterfaze,
        IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType mapperType = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper");
        mapperInterfaze.setVisibility(JavaVisibility.DEFAULT);
        mapperInterfaze.getAnnotations().clear();
        Set<FullyQualifiedJavaType> importTypes = mapperInterfaze.getImportedTypes();
        Set<FullyQualifiedJavaType> newImportTypes = importTypes.stream()
            .filter(t -> !t.equals(mapperType))
            .collect(Collectors.toSet());
        importTypes.clear();
        importTypes.addAll(newImportTypes);

        String targetProject = context.getJavaModelGeneratorConfiguration()
            .getTargetProject();

        FullyQualifiedJavaType daoType = new FullyQualifiedJavaType(mapperInterfaze.getType()
            .getFullyQualifiedName()
            .replaceFirst("Mapper", "Dao"));

        try {
            ShellCallback sc = new DefaultShellCallback(false);
            File fileDir = sc.getDirectory(targetProject, daoType.getPackageName());
            File javaFile = new File(fileDir, daoType.getShortName() + ".java");
            if (javaFile.exists() && javaFile.isFile()) {
                LOG.debug("Existing file " + javaFile.getPath() + ", skips generating this file.");
                return;
            }
        } catch (ShellException e) {
            LOG.error(e.getMessage(), e);
        }

        Interface daoInterface = new Interface(daoType);
        daoInterface.setVisibility(JavaVisibility.PUBLIC);
        daoInterface.addSuperInterface(mapperInterfaze.getType());
        daoInterface.addAnnotation("@Mapper");
        daoInterface.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        GeneratedJavaFile daoInterfaceJavaFile = new GeneratedJavaFile(daoInterface, targetProject,
            context.getJavaFormatter()) {
            public boolean isMergeable() {
                return false;
            }
        };
        generatedJavaFiles.add(daoInterfaceJavaFile);
    }
}
