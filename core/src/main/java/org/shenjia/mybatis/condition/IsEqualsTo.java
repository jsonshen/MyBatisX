package org.shenjia.mybatis.condition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.AbstractListValueCondition;
import org.shenjia.mybatis.util.Strings;

public class IsEqualsTo<T> extends AbstractListValueCondition<T> {
	
	private static final IsEqualsTo<?> EMPTY = new IsEqualsTo<>(Collections.emptyList());

	@SuppressWarnings("unchecked")
    public static <T> IsEqualsTo<T> empty() {
        return (IsEqualsTo<T>) EMPTY;
    }

    public IsEqualsTo(Collection<T> values) {
        super(values);
    }

	@Override
	public String renderCondition(String columnName, Stream<String> placeholders) {
		return placeholders.map(placeholder -> Strings.join(columnName, " = ", placeholder))
		    .collect(Collectors.joining(" or ", "(", ")"));
	}

	@Override
	public AbstractListValueCondition<T> filter(Predicate<? super T> predicate) {
		return filterSupport(predicate, IsEqualsTo::new, this, IsEqualsTo::empty);
	}

	@SafeVarargs
    public static <T> IsEqualsTo<T> of(T... values) {
        return of(Arrays.asList(values));
    }

    public static <T> IsEqualsTo<T> of(Collection<T> values) {
        return new IsEqualsTo<>(values);
    }
}
