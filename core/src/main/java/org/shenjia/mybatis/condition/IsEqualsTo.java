/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
