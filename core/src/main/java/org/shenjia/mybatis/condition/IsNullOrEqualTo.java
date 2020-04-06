package org.shenjia.mybatis.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsNullOrEqualTo<T> extends AbstractSingleValueCondition<T> {

    public IsNullOrEqualTo(Supplier<T> valueSupplier,
        Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }

    public IsNullOrEqualTo(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }

    @Override
    public String renderCondition(String columnName,
        String placeholder) {
        return "(" + columnName + " is null or " + columnName + " = " + placeholder + ")";
    }

}
