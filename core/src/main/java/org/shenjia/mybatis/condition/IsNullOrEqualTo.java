package org.shenjia.mybatis.condition;

import java.util.function.Predicate;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;
import org.shenjia.mybatis.util.Strings;

public class IsNullOrEqualTo<T> extends AbstractSingleValueCondition<T> {
	
	private static final IsNullOrEqualTo<?> EMPTY = new IsNullOrEqualTo<Object>(null) {
        @Override
        public boolean shouldRender() {
            return false;
        }
    };
    
    @SuppressWarnings("unchecked")
    public static <T> IsNullOrEqualTo<T> empty() {
        return (IsNullOrEqualTo<T>) EMPTY;
    }

    public IsNullOrEqualTo(T value) {
        super(value);
    }

	@Override
	public String renderCondition(String columnName, String placeholder) {
		return Strings.join("(", columnName, " is null or ", columnName, "=", placeholder, ")");
	}

	@Override
	public AbstractSingleValueCondition<T> filter(Predicate<? super T> predicate) {
		return filterSupport(predicate, IsNullOrEqualTo::empty, this);
	}

	public static <T> IsNullOrEqualTo<T> of(T value) {
        return new IsNullOrEqualTo<>(value);
    }
}
