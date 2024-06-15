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
package org.shenjia.mybatis.generator.runtime.springjdbc;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import org.mybatis.generator.internal.util.JavaBeansUtil;
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

        TopLevelClass mapperClass = createMapperClass();
        TopLevelClass daoClass = createDaoClass(mapperClass);

//        TopLevelClass supportClass = getSupportClass();
//        String staticImportString = supportClass.getType().getFullyQualifiedNameWithoutTypeParameters() + ".*"; //$NON-NLS-1$
//        interfaze.addStaticImport(staticImportString);

//        if (hasGeneratedKeys) {
//            addBasicInsertMethod(interfaze);
//            addBasicInsertMultipleMethod(interfaze);
//        }

//        boolean reuseResultMap = addBasicSelectManyMethod(interfaze);
//        addBasicSelectOneMethod(interfaze, reuseResultMap);
//        addGeneralCountMethod(interfaze);
//        addGeneralDeleteMethod(interfaze);
//        addDeleteByPrimaryKeyMethod(interfaze);
//        addInsertOneMethod(interfaze);
//        addInsertMultipleMethod(interfaze);
//        addInsertSelectiveMethod(interfaze);
//        addSelectListField(interfaze);
//        addGeneralSelectMethod(interfaze);
//        addSelectDistinctMethod(interfaze);
//        addSelectByPrimaryKeyMethod(interfaze);
//        addGeneralUpdateMethod(interfaze);
//        addUpdateAllMethod(interfaze);
//        addUpdateSelectiveMethod(interfaze);
//        addUpdateByPrimaryKeyMethod(interfaze);
//        addUpdateByPrimaryKeySelectiveMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<>();
//        if (context.getPlugins().clientGenerated(interfaze, introspectedTable)) {
//            answer.add(interfaze);
//        }

//        if (context.getPlugins().dynamicSqlSupportGenerated(supportClass, introspectedTable)) {
//            answer.add(supportClass);
//        }
        
		if (null != mapperClass) {
			answer.add(mapperClass);
		}
		if (null != daoClass) {
			answer.add(daoClass);
		}

        return answer;
    }

    protected void preCalculate() {
        recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result"; //$NON-NLS-1$
        tableFieldName =
                JavaBeansUtil.getValidPropertyName(introspectedTable.getMyBatisDynamicSQLTableObjectName());
        fragmentGenerator = new FragmentGenerator.Builder()
                .withIntrospectedTable(introspectedTable)
                .withResultMapId(resultMapId)
                .withTableFieldName(tableFieldName)
                .build();

        hasGeneratedKeys = introspectedTable.getGeneratedKey().isPresent();
    }

	protected TopLevelClass createMapperClass() {
		FullyQualifiedJavaType superType = new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcMapper<" + recordType.getShortName() + ">");
		FullyQualifiedJavaType clientType = new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcClient");
		FullyQualifiedJavaType mapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		
		TopLevelClass mapperClass = new TopLevelClass(mapperType);
		mapperClass.setSuperClass(superType);
		mapperClass.addImportedType(superType);
		mapperClass.addImportedType(clientType);
		mapperClass.addImportedType(recordType);
		mapperClass.setVisibility(JavaVisibility.DEFAULT);
		context.getCommentGenerator().addJavaFileComment(mapperClass);

		Method constructor = new Method(mapperType.getShortName());
		constructor.addJavaDocLine("");
		constructor.setConstructor(true);
		constructor.addParameter(new Parameter(clientType, "client"));
		constructor.setVisibility(JavaVisibility.PUBLIC);
		constructor.addBodyLine("super(client, new " + recordType.getShortName() + "());");
		mapperClass.addMethod(constructor);
		return mapperClass;
	}
    
    protected TopLevelClass createDaoClass(TopLevelClass mapperClass) {
    	FullyQualifiedJavaType mapperType = mapperClass.getType();
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
		}
		FullyQualifiedJavaType clientType = new FullyQualifiedJavaType("org.shenjia.mybatis.spring.JdbcClient");
		TopLevelClass daoClass = new TopLevelClass(daoType);
        daoClass.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(daoClass);
        daoClass.addImportedType(clientType);
        daoClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        daoClass.addAnnotation("@Component");
        daoClass.setSuperClass(mapperClass.getType());
        
        Method constructor = new Method(daoType.getShortName());
        constructor.addJavaDocLine("");
        constructor.setConstructor(true);
        constructor.addParameter(new Parameter(clientType, "client"));
        constructor.setVisibility(JavaVisibility.PUBLIC);
        constructor.addBodyLine("super(client);");
        daoClass.addMethod(constructor);
        return daoClass;
    }

    protected void addInsertOneMethod(Interface interfaze) {
        InsertMethodGenerator generator = new InsertMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        if (generate(interfaze, generator) && !hasGeneratedKeys) {
            // add common interface
            addCommonInsertInterface(interfaze);
        }
    }

    protected void addCommonInsertInterface(Interface interfaze) {
//        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
//                "org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper<" //$NON-NLS-1$
//                        + recordType.getFullyQualifiedName() + ">"); //$NON-NLS-1$
//        interfaze.addSuperInterface(superInterface);
//        interfaze.addImportedTypes(superInterface.getImportList().stream()
//                .map(FullyQualifiedJavaType::new)
//                .collect(Collectors.toSet()));
    }

    protected void addBasicInsertMultipleMethod(Interface interfaze) {
        BasicMultipleInsertMethodGenerator generator = new BasicMultipleInsertMethodGenerator.Builder()
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
        if (generate(interfaze, generator) && !hasGeneratedKeys) {
            // add common interface
            addCommonInsertInterface(interfaze);
        }
    }

    protected void addGeneralCountMethod(Interface interfaze) {
        GeneralCountMethodGenerator generator = new GeneralCountMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .build();
        if (generate(interfaze, generator)) {
            // add common interface
            FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
                    "org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper"); //$NON-NLS-1$
            interfaze.addSuperInterface(superInterface);
            interfaze.addImportedType(superInterface);
        }
    }

    protected void addGeneralDeleteMethod(Interface interfaze) {
        GeneralDeleteMethodGenerator generator = new GeneralDeleteMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .build();
        if (generate(interfaze, generator)) {
            // add common interface
            FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
                    "org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper"); //$NON-NLS-1$
            interfaze.addSuperInterface(superInterface);
            interfaze.addImportedType(superInterface);
        }
    }

    protected void addSelectListField(Interface interfaze) {
        SelectListGenerator generator = new SelectListGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .build();
        FieldAndImports fieldAndImports = generator.generateFieldAndImports();

        if (fieldAndImports != null && generator.callPlugins(fieldAndImports.getField(), interfaze)) {
            interfaze.addField(fieldAndImports.getField());
            interfaze.addImportedTypes(fieldAndImports.getImports());
        }
    }

    protected void addGeneralSelectMethod(Interface interfaze) {
        addGeneralSelectOneMethod(interfaze);
        GeneralSelectMethodGenerator generator = new GeneralSelectMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addSelectDistinctMethod(Interface interfaze) {
        GeneralSelectDistinctMethodGenerator generator = new GeneralSelectDistinctMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addGeneralSelectOneMethod(Interface interfaze) {
        GeneralSelectOneMethodGenerator generator = new GeneralSelectOneMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addGeneralUpdateMethod(Interface interfaze) {
        GeneralUpdateMethodGenerator generator = new GeneralUpdateMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .build();
        if (generate(interfaze, generator)) {
            // add common interface
            FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
                    "org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper"); //$NON-NLS-1$
            interfaze.addSuperInterface(superInterface);
            interfaze.addImportedType(superInterface);
        }
    }

    protected void addUpdateAllMethod(Interface interfaze) {
        UpdateAllColumnsMethodGenerator generator = new UpdateAllColumnsMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withFragmentGenerator(fragmentGenerator)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addUpdateSelectiveMethod(Interface interfaze) {
        UpdateSelectiveColumnsMethodGenerator generator = new UpdateSelectiveColumnsMethodGenerator.Builder()
            .withContext(context)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withFragmentGenerator(fragmentGenerator)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

    protected void addBasicSelectOneMethod(Interface interfaze, boolean reuseResultMap) {
        BasicSelectOneMethodGenerator generator = new BasicSelectOneMethodGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .withResultMapId(resultMapId)
            .withReuseResultMap(reuseResultMap)
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

        if (generate(interfaze, generator) && !hasGeneratedKeys) {
            // add common interface
            addCommonInsertInterface(interfaze);
        }
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

    protected void addBasicInsertMethod(Interface interfaze) {
        BasicInsertMethodGenerator generator = new BasicInsertMethodGenerator.Builder()
            .withContext(context)
            .withFragmentGenerator(fragmentGenerator)
            .withIntrospectedTable(introspectedTable)
            .withTableFieldName(tableFieldName)
            .withRecordType(recordType)
            .build();
        generate(interfaze, generator);
    }

	protected boolean addBasicSelectManyMethod(Interface interfaze) {
		BasicSelectManyMethodGenerator generator = new BasicSelectManyMethodGenerator.Builder()
			.withContext(context)
		    .withFragmentGenerator(fragmentGenerator)
		    .withIntrospectedTable(introspectedTable)
		    .withTableFieldName(tableFieldName)
		    .withRecordType(recordType)
		    .build();
		return generate(interfaze, generator);
	}

    protected boolean generate(Interface interfaze, AbstractMethodGenerator generator) {
        MethodAndImports mi = generator.generateMethodAndImports();
        if (mi != null && generator.callPlugins(mi.getMethod(), interfaze)) {
            interfaze.addMethod(mi.getMethod());
            interfaze.addImportedTypes(mi.getImports());
            interfaze.addStaticImports(mi.getStaticImports());
            return true;
        }
        return false;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return null;
    }
}
