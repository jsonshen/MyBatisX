package org.shenjia.mybatis.sql;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import org.shenjia.mybatis.condition.IsEqualsTo;
import org.shenjia.mybatis.condition.IsNullOrEqualTo;

public interface DynamicSqlBuilder {

    static <T> IsNullOrEqualTo<T> isNullOrEqualTo(T value) {
        return isNullOrEqualTo(() -> value);
    }

    static <T> IsNullOrEqualTo<T> isNullOrEqualTo(Supplier<T> valueSupplier) {
        return new IsNullOrEqualTo<>(valueSupplier);
    }

    @SafeVarargs
    static <T> IsEqualsTo<T> isEqualsTo(T...values) {
        return isEqualsTo(Arrays.asList(values));
    }

    static <T> IsEqualsTo<T> isEqualsTo(Collection<T> values) {
        return new IsEqualsTo<>(values);
    }
}
