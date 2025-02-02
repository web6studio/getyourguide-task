package com.getyourguide.demo;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> data;
    private int total;
    private int offset;
    private int limit;

    public PaginatedResponse(List<T> data, int total, int offset, int limit) {
        this.data = data;
        this.total = total;
        this.offset = offset;
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
