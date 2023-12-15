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
