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
import org.mybatis.generator.api.dom.java.Method;

public class MethodsAndImports {

    private final Set<Method> methods;
    private final Set<FullyQualifiedJavaType> imports;
    private final Set<String> staticImports;

    private MethodsAndImports(Builder builder) {
    	this.methods = builder.methods;
    	this.imports = builder.imports;
    	this.staticImports = builder.staticImports;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public Set<FullyQualifiedJavaType> getImports() {
        return imports;
    }

    public Set<String> getStaticImports() {
        return staticImports;
    }

    public static Builder withMethod(Method method) {
        return new Builder().withMethod(method);
    }
    
    public static Builder withMethods(Set<Method> methods) {
        return new Builder().withMethods(methods);
    }
    
    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
    	
        private Set<Method> methods = new HashSet<>();
        private final Set<FullyQualifiedJavaType> imports = new HashSet<>();
        private final Set<String> staticImports = new HashSet<>();

        public Builder withMethod(Method method) {
            this.methods.add(method);
            return this;
        }
        
        public Builder withMethods(Set<Method> methods) {
            this.methods.addAll(methods);
            return this;
        }

        public Builder withImport(FullyQualifiedJavaType importedType) {
            this.imports.add(importedType);
            return this;
        }

        public Builder withImports(Set<FullyQualifiedJavaType> imports) {
            this.imports.addAll(imports);
            return this;
        }

        public Builder withStaticImport(String staticImport) {
            this.staticImports.add(staticImport);
            return this;
        }

        public Builder withStaticImports(Set<String> staticImports) {
            this.staticImports.addAll(staticImports);
            return this;
        }

        public MethodsAndImports build() {
            return new MethodsAndImports(this);
        }
    }
}
