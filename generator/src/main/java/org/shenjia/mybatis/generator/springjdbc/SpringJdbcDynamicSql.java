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

import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

public class SpringJdbcDynamicSql extends IntrospectedTableMyBatis3Impl {

    public SpringJdbcDynamicSql() {
        targetRuntime = null;
    }

    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
            List<String> warnings,
            ProgressCallback progressCallback) {
        // no XML with dynamic SQL support
        xmlMapperGenerator = null;
    }

    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }

        return new SpringJdbcDynamicSqlMapperGenerator(getClientProject());
    }

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {

        AbstractJavaGenerator javaGenerator = new SpringJdbcDynamicSqlModelGenerator(getModelProject());
        initializeAbstractGenerator(javaGenerator, warnings,
                progressCallback);
        javaGenerators.add(javaGenerator);
    }

    @Override
    public boolean requiresXMLGenerator() {
        return false;
    }
}
