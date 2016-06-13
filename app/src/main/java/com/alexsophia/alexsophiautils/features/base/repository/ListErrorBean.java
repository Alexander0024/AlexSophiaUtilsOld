package com.alexsophia.alexsophiautils.features.base.repository;

/**
 * Created by liuweiping on 2016-3-21.
 * List列表数据集的解析公共类,信息条数字段为total_number
 */
public abstract class ListErrorBean extends ErrorBean {
    private int next_cursor;
    private int previous_cursor;
    private int total_number;

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
