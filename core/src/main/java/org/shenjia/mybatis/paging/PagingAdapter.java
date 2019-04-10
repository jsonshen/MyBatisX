package org.shenjia.mybatis.paging;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Function;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class PagingAdapter<R> {

    private SelectModel selectModel;
    private Function<SelectStatementProvider, Long> countMethod;
    private Function<SelectStatementProvider, List<R>> selectManyMethod;
    private final long currentPage;
    private final int pageSize;

    private PagingAdapter(SelectModel selectModel,
        Function<SelectStatementProvider, Long> mapperCountMethod,
        Function<SelectStatementProvider, List<R>> mapperSelectManyMethod,
        long currentPage,
        int pageSize) {
        this.selectModel = Objects.requireNonNull(selectModel);
        this.countMethod = Objects.requireNonNull(mapperCountMethod);
        this.selectManyMethod = Objects.requireNonNull(mapperSelectManyMethod);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public static <R> PagingAdapter<R> of(SelectModel selectModel,
        Function<SelectStatementProvider, Long> mapperCountMethod,
        Function<SelectStatementProvider, List<R>> mapperSelectManyMethod,
        long currentPage,
        int pageSize) {
        return new PagingAdapter<>(selectModel, mapperCountMethod, mapperSelectManyMethod, currentPage, pageSize);
    }

    public List<R> executeRangeQuery() {
        SelectStatementProvider ssp = selectModel.render(RenderingStrategy.MYBATIS3);
        return selectManyMethod.apply(pagingDecorator().decorate(ssp, currentPage, pageSize));
    }

    public Page<R> executePageQuery() {
        SelectStatementProvider ssp = selectModel.render(RenderingStrategy.MYBATIS3);
        Long totalCount = countMethod.apply(new SelectStatementProvider() {
            @Override
            public Map<String, Object> getParameters() {
                return ssp.getParameters();
            }

            @Override
            public String getSelectStatement() {
                return "select count (t.*) from (" + ssp.getSelectStatement() + ") t";
            }
        });
        Page<R> page = new Page<>(currentPage, pageSize, totalCount);
        if (totalCount > 0 && ((currentPage - 1) * pageSize < totalCount)) {
            List<R> data = selectManyMethod.apply(pagingDecorator().decorate(ssp, currentPage, pageSize));
            page.setData(data);
        }
        return page;
    }

    public PagingDecorator pagingDecorator() {
        // Create a PagingDecorator by System property.
        String interfaceName = PagingDecorator.class.getName();
        String implClassName = System.getProperty(interfaceName);
        if (null != implClassName) {
            try {
                return (PagingDecorator) Class.forName(implClassName)
                    .getDeclaredConstructor()
                    .newInstance();
            } catch (Exception e) {
                throw new PagingException(
                    e.getMessage() + ", System.getProperty(\"" + interfaceName + "\"); return is " + implClassName);
            }
        }
        // Create a PagingDecorator by SPI.
        Iterator<PagingDecorator> iterator = ServiceLoader.load(PagingDecorator.class)
            .iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        // Not found PagingDecorator
        throw new PagingException("Please define a paging decorator, Method 1 has a higher priority. "
            + "Method 1: Call System.setProperty(\"" + interfaceName + "\", pagingDecoratorClassName); method."
            + "Method 2: Set the META-INF/services/" + interfaceName + " file.");
    }
}
