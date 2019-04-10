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
