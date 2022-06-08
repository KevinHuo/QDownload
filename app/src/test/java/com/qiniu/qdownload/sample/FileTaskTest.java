package com.qiniu.qdownload.sample;


import org.junit.Test;

public class FileTaskTest {
    @Test
    public void initTest() {
        System.out.println("hk --------- getBlockSize : " + getBlockSize());
    }

    public long getBlockSize() {
        return 1L << 18;
    }
}
