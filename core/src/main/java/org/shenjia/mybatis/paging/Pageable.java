package org.shenjia.mybatis.paging;

public class Pageable {

    private long currentPage;
    private int pageSize;

    public Pageable(long currentPage,
        int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
