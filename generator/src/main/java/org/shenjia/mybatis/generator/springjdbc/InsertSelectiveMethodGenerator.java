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
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class InsertSelectiveMethodGenerator extends AbstractMethodGenerator {
	
    private final FullyQualifiedJavaType recordType;
    private final String tableFieldName;

    private InsertSelectiveMethodGenerator(Builder builder) {
        super(builder);
        this.recordType = builder.recordType;
        this.tableFieldName = builder.getTableFieldName();
        
    }

	@Override
	public MethodsAndImports generateMethodAndImports() {
		Set<FullyQualifiedJavaType> imports = new HashSet<>();
		imports.add(recordType);
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlBuilder"));

		Method method = new Method("insertSelective");
		method.setDefault(true);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "tableName"));
		method.addParameter(new Parameter(recordType, "record"));
		method.addBodyLine("return client().insert(SqlBuilder.insert(record)");
		method.addBodyLine("	.into(targetTable(tableName))");

		List<IntrospectedColumn> columns = ListUtilities
		    .removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
		for (IntrospectedColumn column : columns) {
			String fieldName = column.getActualColumnName();
			if (column.isSequenceColumn()) {
				method.addBodyLine("    .map(" + tableFieldName + "." + fieldName + ").toProperty(\"" + column.getJavaProperty() + "\")");
			} else {
				String methodName = JavaBeansUtil.getGetterMethodName(column.getJavaProperty(),
				    column.getFullyQualifiedJavaType());
				method.addBodyLine("    .map(" + tableFieldName + "." + fieldName + ")" + ".toPropertyWhenPresent(\""
				    + column.getJavaProperty() + "\", record::" + methodName + ")");
			}
		}
		method.addBodyLine(");");

		return MethodsAndImports.withMethod(method)
		    .withImports(imports)
		    .withStaticImport(recordType.getFullyQualifiedName() + "." + tableFieldName)
		    .build();
	}

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder> {
        private FullyQualifiedJavaType recordType;

        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        public InsertSelectiveMethodGenerator build() {
            return new InsertSelectiveMethodGenerator(this);
        }
    }
}
