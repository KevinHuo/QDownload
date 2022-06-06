package com.qiniu.qdownload.core.download;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private static final int BLOCK_SIZE = 256  * 1024;

    private String url;
    private Range range;
    private boolean isDone;

    static public List<Block> getBlocks(String url, long contentSize) {
        if (contentSize <= 0) {
            return null;
        }

        List<Block> list = new ArrayList<>();
        if (contentSize <= BLOCK_SIZE) {
            list.add(new Block(url, new Range(0, contentSize)));
        } else {
            int curByte = 0;
            while (contentSize - curByte > BLOCK_SIZE) {
                list.add(new Block(url, new Range(curByte, curByte + BLOCK_SIZE)));
                curByte += BLOCK_SIZE + 1;
            }
            list.add(new Block(url, new Range(curByte, contentSize)));
        }
        return list;
    }

    public String getUrl() {
        return url;
    }

    public Block(String url, Range range) {
        this.url = url;
        this.range = range;
    }

    public long getLeft() {
        return range.getLeft();
    }

    public long getRight() {
        return range.getRight();
    }

    public String getRangeString() {
        System.out.println("hk ------------ range : " + "bytes=" + getLeft() + "-" + getRight());
        return "bytes=" + getLeft() + "-" + getRight();
    }
}
