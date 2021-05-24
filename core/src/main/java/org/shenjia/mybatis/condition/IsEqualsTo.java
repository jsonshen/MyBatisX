package org.shenjia.mybatis.condition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.AbstractListValueCondition;
import org.mybatis.dynamic.sql.Callback;

public class IsEqualsTo<T> extends AbstractListValueCondition<T> {
	
	private static final IsEqualsTo<?> EMPTY = new IsEqualsTo<>(Collections.emptyList());

	@SuppressWarnings("unchecked")
    public static <T> IsEqualsTo<T> empty() {
        return (IsEqualsTo<T>) EMPTY;
    }

    public IsEqualsTo(Collection<T> values) {
        super(values);
    }

    public IsEqualsTo(Collection<T> values, Callback emptyCallback) {
		super(values, emptyCallback);
	}

	@Override
    public String renderCondition(String columnName,
        Stream<String> placeholders) {
        return placeholders.map(placeholder -> columnName + " = " + placeholder)
            .collect(Collectors.joining(" or ", "(", ")"));
    }

	@Override
	public AbstractListValueCondition<T> filter(Predicate<? super T> predicate) {
		return filterSupport(predicate, IsEqualsTo::new, this, IsEqualsTo::empty);
	}

	@Override
	public AbstractListValueCondition<T> withListEmptyCallback(Callback callback) {
		return new IsEqualsTo<>(values, callback);
	}

	@SafeVarargs
    public static <T> IsEqualsTo<T> of(T... values) {
        return of(Arrays.asList(values));
    }

    public static <T> IsEqualsTo<T> of(Collection<T> values) {
        return new IsEqualsTo<>(values);
    }
}
