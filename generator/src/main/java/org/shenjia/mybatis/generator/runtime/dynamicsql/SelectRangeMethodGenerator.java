package org.shenjia.mybatis.generator.runtime.dynamicsql;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class SelectRangeMethodGenerator extends AbstractMethodGenerator {

	private FullyQualifiedJavaType recordType;
    private FragmentGenerator fragmentGenerator;
    
    private SelectRangeMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateSelectByExampleWithBLOBs() 
                && !introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<FullyQualifiedJavaType>();
        imports.add(new FullyQualifiedJavaType("java.util.Optional"));
        imports.add(new FullyQualifiedJavaType("java.util.function.Function"));
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.QueryExpressionDSL"));
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SortSpecification"));
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSL"));
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectModel"));
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.WhereApplier"));
        imports.add(new FullyQualifiedJavaType("org.shenjia.mybatis.paging.RangeAdapter"));
        imports.add(FullyQualifiedJavaType.getNewListInstance());
        imports.add(recordType);
        
        Method method = new Method("selectRange");
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setDefault(true);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("long"), "currentPage"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "pageSize"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("WhereApplier"), "where"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("SortSpecification..."), "columns"));
        method.setReturnType(new FullyQualifiedJavaType("List<" + recordType.getShortNameWithoutTypeArguments() + ">"));
        
		StringBuilder buf = new StringBuilder();
		buf.append("Function<SelectModel, RangeAdapter<");
		buf.append(recordType.getShortNameWithoutTypeArguments());
		buf.append(">> adapter = model -> RangeAdapter.of(model, this::selectMany, currentPage, pageSize);");
		method.addBodyLine(buf.toString());
		
		buf = new StringBuilder();
		buf.append("QueryExpressionDSL<RangeAdapter<");
		buf.append(recordType.getShortNameWithoutTypeArguments());
		buf.append(">> dsl = SelectDSL.select(adapter, ");
		buf.append(fragmentGenerator.getSelectList());
		buf.append(")");
		method.addBodyLine(buf.toString());
		
		method.addBodyLine("    .from(" + tableFieldName + ");");
		method.addBodyLine("Optional.ofNullable(where).ifPresent(wa -> dsl.applyWhere(wa));");
		method.addBodyLine("Optional.ofNullable(columns).filter(cols -> cols.length > 0).ifPresent(cols -> dsl.orderBy(cols));");
		method.addBodyLine("return dsl.build().execute();");
        
		return MethodAndImports.withMethod(method).withImports(imports).build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return true;
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
        
        public SelectRangeMethodGenerator build() {
            return new SelectRangeMethodGenerator(this);
        }
    }
}
