package org.shenjia.mybatis.generator.runtime.dynamic.sql.elements;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class SelectPageMethodGenerator extends AbstractMethodGenerator {

	private FullyQualifiedJavaType recordType;
	private String tableFieldName;
	private FragmentGenerator fragmentGenerator;

	private SelectPageMethodGenerator(Builder builder) {
		super(builder);
		recordType = builder.recordType;
		tableFieldName = builder.tableFieldName;
		fragmentGenerator = builder.fragmentGenerator;
	}

	@Override
	public MethodAndImports generateMethodAndImports() {
		if (!introspectedTable.getRules().generateSelectByExampleWithBLOBs()
		    && !introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
			return null;
		}

		Set<FullyQualifiedJavaType> imports = new HashSet<FullyQualifiedJavaType>();
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SortSpecification")); //$NON-NLS-1$
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSL")); //$NON-NLS-1$
		imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.WhereApplier")); //$NON-NLS-1$
		imports.add(new FullyQualifiedJavaType("org.shenjia.mybatis.paging.PageAdapter")); //$NON-NLS-1$
		imports.add(new FullyQualifiedJavaType("org.shenjia.mybatis.paging.Page")); //$NON-NLS-1$
		imports.add(FullyQualifiedJavaType.getNewListInstance());
		imports.add(recordType);

		Method method = new Method("selectPage"); //$NON-NLS-1$
		method.setDefault(true);
		context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("long"), "currentPage"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "pageSize"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("WhereApplier"), "where"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("SortSpecification..."), "columns"));

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Page<" //$NON-NLS-1$
		    + recordType.getShortNameWithoutTypeArguments() + ">"); //$NON-NLS-1$
		method.setReturnType(returnType);
		StringBuilder sb = new StringBuilder();
		sb.append(
		    "return SelectDSL.select(selectModel -> PageAdapter.of(selectModel, this::count, this::selectMany, currentPage, pageSize), "); //$NON-NLS-1$
		sb.append(fragmentGenerator.getSelectList());
		sb.append(')');
		method.addBodyLine(sb.toString());
		method.addBodyLine("        .from(" + tableFieldName + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		method.addBodyLine("        .applyWhere(where)"); //$NON-NLS-1$ //$NON-NLS-2$
		method.addBodyLine("        .orderBy(columns)"); //$NON-NLS-1$ //$NON-NLS-2$
		method.addBodyLine("        .build()");
		method.addBodyLine("        .execute();");

		return MethodAndImports.withMethod(method).withImports(imports).build();
	}

	@Override
	public boolean callPlugins(Method method, Interface interfaze) {
		return context.getPlugins().clientSelectByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
	}

    public static class Builder extends BaseBuilder<Builder> {
        private FullyQualifiedJavaType recordType;
        private String tableFieldName;
        private FragmentGenerator fragmentGenerator;
        
        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        public Builder withTableFieldName(String tableFieldName) {
            this.tableFieldName = tableFieldName;
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

        public SelectPageMethodGenerator build() {
            return new SelectPageMethodGenerator(this);
        }
    }

}
