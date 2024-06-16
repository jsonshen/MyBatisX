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
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class SelectByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    private final FullyQualifiedJavaType recordType;
    private final FragmentGenerator fragmentGenerator;

    private SelectByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodsAndImports generateMethodAndImports() {
        if (!Utils.generateSelectByPrimaryKey(introspectedTable)) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<Method> methods = new HashSet<>();

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.Optional"); //$NON-NLS-1$
        returnType.addTypeArgument(recordType);
        imports.add(returnType);

        MethodParts methodParts = fragmentGenerator.getPrimaryKeyWhereClauseAndParameters();
        MethodsAndImports.Builder builder = MethodsAndImports.create()
            .withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo") //$NON-NLS-1$
            .withImports(imports);
        
        Method method1 = new Method("selectByPrimaryKey"); //$NON-NLS-1$
        method1.setDefault(true);
        method1.setReturnType(returnType);
        method1.addBodyLine("return selectOne(c ->"); //$NON-NLS-1$
        acceptParts(builder, method1, methodParts);
        methods.add(method1);
        
        Method method2 = new Method("selectByPrimaryKey"); //$NON-NLS-1$
        method2.setDefault(true);
        method2.setReturnType(returnType);
        method2.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "tableName"));
        method2.addBodyLine("return selectOne(tableName, c ->"); //$NON-NLS-1$
        acceptParts(builder, method2, methodParts);
        methods.add(method2);
        
        return builder.withMethods(methods).build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder> {
        private FullyQualifiedJavaType recordType;
        private FragmentGenerator fragmentGenerator;

        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return this;
        }

        public Builder withFragmentGenerator(FragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        public SelectByPrimaryKeyMethodGenerator build() {
            return new SelectByPrimaryKeyMethodGenerator(this);
        }
    }
}
