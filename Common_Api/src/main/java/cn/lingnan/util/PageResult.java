package cn.lingnan.util;

import java.util.List;

public class PageResult<T> {

    /**
     * 查询的结果总数数
     */
    private Long total;
    /**
     * 查询结果分页后共多少页数据
     */
    private Integer totalPage;
    /**
     * 分页结果集
     */
    private List<T> items;

    public PageResult() {}

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
