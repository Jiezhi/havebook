package io.github.jiezhi.havebook.model;

import java.util.List;

/**
 * Function
 *
 * @author Jiezhi
 * @version 1.0, 22/10/2016
 */
public class DoubanSearchModel {
    private static final String TAG = DoubanSearchModel.class.getSimpleName();

    private int start;
    private int count;
    private int total;
    private List<DoubanBookModel> books;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public List<DoubanBookModel> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBookModel> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "DoubanSearchModel{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", bookList=" + books +
                '}';
    }
}
