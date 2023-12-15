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
package org.shenjia.mybatis.paging;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.shenjia.mybatis.sql.SqlExecutor;

public class RangeAdapter<R> implements
    SqlExecutor<List<R>> {

    private SelectModel selectModel;
    private Function<SelectStatementProvider, List<R>> selectManyMethod;
    private final long currentPage;
    private final int pageSize;

    private RangeAdapter(SelectModel selectModel,
        Function<SelectStatementProvider, List<R>> selectManyMethod,
        long currentPage,
        int pageSize) {
        this.selectModel = Objects.requireNonNull(selectModel);
        this.selectManyMethod = Objects.requireNonNull(selectManyMethod);
        this.currentPage = Objects.requireNonNull(currentPage);
        this.pageSize = Objects.requireNonNull(pageSize);
    }

    public static <R> RangeAdapter<R> of(SelectModel selectModel,
        Function<SelectStatementProvider, List<R>> selectManyMethod,
        long currentPage,
        int pageSize) {
        return new RangeAdapter<>(selectModel, selectManyMethod, currentPage, pageSize);
    }

    @Override
    public List<R> execute() {
        SelectStatementProvider ssp = selectModel.render(RenderingStrategies.MYBATIS3);
        return selectManyMethod.apply(decorator(PagingDecorator.class).decorate(ssp, currentPage, pageSize));
    }

}
