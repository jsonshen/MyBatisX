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

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

public class SpringJdbcDynamicSqlMapperGenerator extends AbstractJavaClientGenerator {
	
	private static final Log LOG = LogFactory.getLog(SpringJdbcDynamicSqlMapperGenerator.class);

    // record type for insert, select, update
    protected FullyQualifiedJavaType recordType;

    // id to use for the common result map
    protected String resultMapId;

    // name of the field containing the table in the support class
    protected String tableFieldName;

    protected FragmentGenerator fragmentGenerator;

    protected boolean hasGeneratedKeys;

    public SpringJdbcDynamicSqlMapperGenerator(String project) {
        super(project, false);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
		progressCallback.startTask(getString("Progress.17", introspectedTable.getFullyQualifiedTable().toString()));
        preCalculate();
        Interface mapperIface = createMapperInterface();
        addDeleteByPrimaryKeyMethod(mapperIface);
        addInsertOneMethod(mapperIface);
        addInsertSelectiveMethod(mapperIface);
        addInsertMultipleMethod(mapperIface);
        addSelectByPrimaryKeyMethod(mapperIface);
        addUpdateByPrimaryKeyMethod(mapperIface);
        addUpdateByPrimaryKeySelectiveMethod(mapperIface);
		List<CompilationUnit> answer = new ArrayList<>();
		if (context.getPlugins().clientGenerated(mapperIface, introspectedTable)) {
			mapperIface.getMethods().get(0).addJavaDocLine("");
			answer.add(mapperIface);
			Optional.ofNullable(createDaoClass(mapperIface)).ifPresent(daoCls -> answer.add(daoCls));
		}
        return answer;
    }

	protected void preCalculate() {
		this.recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		this.resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result";
		this.tableFieldName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		this.fragmentGenerator = new FragmentGenerator.Builder().withIntrospectedTable(introspectedTable)
		    .withResultMapId(resultMapId)
		    .withTableFieldName(tableFieldName)
		    .build();
		this.hasGeneratedKeys = introspectedTable.getGeneratedKey().isPresent();
	}

	protected Interface createMapperInterface() {
		Interface mapperIface = new Interface(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
		context.getCommentGenerator().addJavaFileComment(mapperIface);
		mapperIface.addImportedType(new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcMapper"));
		mapperIface.addImportedType(recordType);
		mapperIface.setVisibility(JavaVisibility.DEFAULT);
		mapperIface.addSuperInterface(new FullyQualifiedJavaType("JdbcMapper<" + recordType.getShortName() + ">"));
		mapperIface.addFileCommentLine("// @formatter:off");
		return mapperIface;
	}
    
    protected TopLevelClass createDaoClass(Interface mapperIface) {
    	FullyQualifiedJavaType mapperType = mapperIface.getType();
		FullyQualifiedJavaType daoType = new FullyQualifiedJavaType(
		    mapperType.getPackageName() + "." + recordType.getShortName() + "Dao");
		try {
			String project = context.getJavaModelGeneratorConfiguration().getTargetProject();
			ShellCallback sc = new DefaultShellCallback(false);
			File fileDir = sc.getDirectory(project, daoType.getPackageName());
			File javaFile = new File(fileDir, daoType.getShortName() + ".java");
			if (javaFile.exists() && javaFile.isFile()) {
				LOG.debug("Existing file " + javaFile.getPath() + ", skips generating this file.");
				return null;
			}
		} catch (ShellException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		FullyQualifiedJavaType clientType = new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcClient");
		TopLevelClass daoCls = new TopLevelClass(daoType);
        daoCls.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(daoCls);
        daoCls.addImportedType(clientType);
        daoCls.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        daoCls.addImportedType(new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcDao"));
        daoCls.addImportedType(recordType);
        daoCls.addAnnotation("@Component");
        daoCls.addSuperInterface(mapperIface.getType());
        daoCls.setSuperClass(new FullyQualifiedJavaType("JdbcDao<" + recordType.getShortName() + ">"));
        
        Method constructor = new Method(daoType.getShortName());
        constructor.addJavaDocLine("");
        constructor.setConstructor(true);
        constructor.addParameter(new Parameter(clientType, "client"));
        constructor.setVisibility(JavaVisibility.PUBLIC);
		constructor.addBodyLine("super(client, new " + recordType.getShortName() + "());");
        daoCls.addMethod(constructor);
        
        return daoCls;
    }

    protected void addInsertOneMethod(Interface interfaze) {
        InsertMethodGenerator generator = new InsertMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        	generate(interfaze, generator);
    }

    protected void addInsertMultipleMethod(Interface interfaze) {
        InsertMultipleMethodGenerator generator = new InsertMultipleMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addDeleteByPrimaryKeyMethod(Interface interfaze) {
        DeleteByPrimaryKeyMethodGenerator generator = new DeleteByPrimaryKeyMethodGenerator.Builder()
                .withContext(context)
                .withIntrospectedTable(introspectedTable)
                .withFragmentGenerator(fragmentGenerator)
                .withTableFieldName(tableFieldName)
                .build();
        generate(interfaze, generator);
    }

    protected void addInsertSelectiveMethod(Interface interfaze) {
        InsertSelectiveMethodGenerator generator = new InsertSelectiveMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addSelectByPrimaryKeyMethod(Interface interfaze) {
        SelectByPrimaryKeyMethodGenerator generator = new SelectByPrimaryKeyMethodGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addUpdateByPrimaryKeyMethod(Interface interfaze) {
        UpdateByPrimaryKeyMethodGenerator generator = new UpdateByPrimaryKeyMethodGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(Interface interfaze) {
        UpdateByPrimaryKeySelectiveMethodGenerator generator =
                new UpdateByPrimaryKeySelectiveMethodGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected boolean generate(Interface mapperIface, AbstractMethodGenerator generator) {
    	boolean result = false;
        MethodsAndImports mi = generator.generateMethodAndImports();
		if (mi != null) {
			for (Method method : mi.getMethods()) {
				if (mi != null && generator.callPlugins(method, mapperIface)) {
		            mapperIface.addMethod(method);
		            result = true;
		        }
			}
			if (result) {
				mapperIface.addImportedTypes(mi.getImports());
	            mapperIface.addStaticImports(mi.getStaticImports());
			}
		}
        return result;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return null;
    }
}
