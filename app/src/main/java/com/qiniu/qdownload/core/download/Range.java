package com.qiniu.qdownload.core.download;

public class Range {
    private long left;
    private long right;

    public long getLeft() {
        return left;
    }

    public long getRight() {
        return right;
    }

    public Range(long left, long right) {
        this.left = left;
        this.right = right;
    }
}
