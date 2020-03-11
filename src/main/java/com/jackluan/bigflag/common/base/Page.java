package com.jackluan.bigflag.common.base;

import com.jackluan.bigflag.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jack.luan
 * @Date: 2020/3/11 23:24
 */
public class Page<T extends BaseDto> implements Serializable {
    private static final int MAX_LIMIT = 1000;
    private Integer start;
    private Integer pageIndex;
    private Integer limit = 20;
    private Integer total = 0;
    private List<T> results = new ArrayList();
    private T condition;

    public Page() {
    }

    public Page(T t) {
        this.condition = t;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        if (null != limit && limit > MAX_LIMIT) {
            throw new RuntimeException("limit greate than 1000");
        } else {
            this.limit = limit;
        }
    }

    public Integer getStart() {
        if (null != this.start && this.start >= 0) {
            return this.start;
        } else {
            if (null == this.pageIndex || this.pageIndex <= 0) {
                this.pageIndex = 1;
            }

            return (this.pageIndex - 1) * this.limit;
        }
    }

    public void setStart(Integer start) {
        if (null != start && start < 0) {
            throw new RuntimeException("start should be gt or eq 0");
        } else {
            this.start = start;
        }
    }

    public T getPageCondition() {
        if (null != this.condition){
            this.condition.setLimit(null);
            this.condition.setStart(null);
        }
        return this.condition;
    }

    public T getCondition() {
        if (null != this.condition){
            this.condition.setLimit(this.getLimit());
            this.condition.setStart(this.getStart());
        }
        return this.condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResults() {
        return this.results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public static int getMaxLimit() {
        return 1000;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        if (null != pageIndex && pageIndex <= 0) {
            throw new RuntimeException("pageIndex should be gt or eq 1");
        } else {
            this.pageIndex = pageIndex;
        }
    }

    public static <R extends BaseDto> Page<R> mapper(Page<?> biz) {
        Page<R> pageTmp = new Page();
        pageTmp.setLimit(biz.getLimit());
        pageTmp.setPageIndex(biz.getPageIndex());
        pageTmp.setStart(biz.getStart());
        pageTmp.setTotal(biz.getTotal());
        return pageTmp;
    }

    public static <R extends BaseDto> Page<R> mapper(Page<?> source, Page<R> target) {
        target.setLimit(source.getLimit());
        target.setPageIndex(source.getPageIndex());
        target.setStart(source.getStart());
        target.setTotal(source.getTotal());
        return target;
    }
}
