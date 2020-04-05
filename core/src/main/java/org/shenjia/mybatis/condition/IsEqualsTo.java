package org.shenjia.mybatis.condition;

import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.AbstractListValueCondition;

public class IsEqualsTo<T> extends AbstractListValueCondition<T> {

    public IsEqualsTo(Collection<T> values,
        UnaryOperator<Stream<T>> valueStreamTransformer) {
        super(values, valueStreamTransformer);
    }

    public IsEqualsTo(Collection<T> values) {
        super(values);
    }

    @Override
    public String renderCondition(String columnName,
        Stream<String> placeholders) {
        return placeholders.map(placeholder -> columnName + " = " + placeholder)
            .collect(Collectors.joining(" or ", "(", ")"));
    }

}
