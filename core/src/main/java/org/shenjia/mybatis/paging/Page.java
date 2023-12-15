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

import java.io.Serializable;
import java.util.List;

public class Page<T> extends Pageable implements
    Serializable {

    private static final long serialVersionUID = 1L;

    private long totalCount;
    private long totalPages;
    private int[] showPages;
    private List<T> data;

    public Page(long currentPage,
        int pageSize,
        long totalCount) {
        super(currentPage, pageSize);
        this.setTotalCount(totalCount);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        if (totalCount < 1) {
            return;
        }
        this.totalCount = totalCount;
        this.totalPages = totalCount / getPageSize();
        if (totalCount % getPageSize() > 0) {
            this.totalPages++;
        }
    }

    public long getTotalPages() {
        return totalPages;
    }

    public boolean isPrevPage() {
        return getCurrentPage() > 1;
    }

    public boolean isNextPage() {
        return getCurrentPage() < totalPages;
    }

    public int[] getShowPages() {
        return showPages;
    }

    public void setShowPages(int[] showPages) {
        this.showPages = showPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
