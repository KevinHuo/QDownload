package com.qiniu.qdownload.core.download;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private static final int BLOCK_SIZE = 256 * 1024;

    private String url;
    private String path;
    private Range range;
    private boolean isDone;
    private int index;

    static public List<Block> getBlocks(String url, long contentSize, String path) {
        if (contentSize <= 0) {
            return null;
        }

        List<Block> list = new ArrayList<>();
        int index = 0;

        if (contentSize <= BLOCK_SIZE) {
            list.add(new Block(url, new Range(0, contentSize), path, index));
        } else {
            int curByte = 0;
            while (contentSize - curByte > BLOCK_SIZE) {
                list.add(new Block(url, new Range(curByte, curByte + BLOCK_SIZE), path, index));
                curByte += BLOCK_SIZE + 1;
                index += 1;
            }
            list.add(new Block(url, new Range(curByte, contentSize), path, index));
        }
        return list;
    }

    public String getUrl() {
        return url;
    }

    public int getIndex() {
        return index;
    }

    public Block(String url, Range range, String path, int index) {
        this.url = url;
        this.range = range;
        this.path = path;
        this.index = index;
    }

    public boolean isDone() {
        return isDone;
    }

    public void done() {
        isDone = true;
    }

    public long getLeft() {
        return range.getLeft();
    }

    public long getRight() {
        return range.getRight();
    }

    public String getPath() {
        return path;
    }

    public String getRangeString() {
        return "bytes=" + getLeft() + "-" + getRight();
    }
}
