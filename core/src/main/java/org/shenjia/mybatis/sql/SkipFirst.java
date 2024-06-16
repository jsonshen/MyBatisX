package org.shenjia.mybatis.sql;

import java.util.Optional;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.DerivedColumn;
import org.mybatis.dynamic.sql.exception.InvalidSqlException;
import org.mybatis.dynamic.sql.render.RenderingContext;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;
import org.mybatis.dynamic.sql.util.Messages;
import org.shenjia.mybatis.util.Strings;

public class SkipFirst implements BasicColumn {

	private BasicColumn column;
	private long skip;
	private int first;
	private RenderingStrategy renderer;

	private SkipFirst(BasicColumn column, long skip, int first, RenderingStrategy renderer) {
		this.column = column;
		this.skip = skip;
		this.first = first;
		this.renderer = renderer;
	}

	@Override
	public Optional<String> alias() {
		return Optional.empty();
	}

	@Override
	public BasicColumn as(String alias) {
		throw new InvalidSqlException(Messages.getString("ERROR.38"));
	}

	@Override
	public FragmentAndParameters render(RenderingContext context) {
		String fragment;
		if (renderer.equals(RenderingStrategies.SPRING_NAMED_PARAMETER)) {
			fragment = "skip :skip first :first ";
		} else {
			fragment = "skip #{parameters.skip} first #{parameters.first} ";
		}
		return FragmentAndParameters
		    .withFragment(Strings.join(fragment, column.render(context).fragment()))
		    .withParameter("skip", skip)
		    .withParameter("first", first)
		    .build();
	}
	
	public static SkipFirst of(String column, long skip, int first, RenderingStrategy renderer) {
		return new SkipFirst(DerivedColumn.of(column), skip, first, renderer);
	}
	
	public static SkipFirst of(BasicColumn column, long skip, int first, RenderingStrategy renderer) {
		return new SkipFirst(column, skip, first, renderer);
	}
}
