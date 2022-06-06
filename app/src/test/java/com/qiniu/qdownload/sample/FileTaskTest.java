package com.qiniu.qdownload.sample;

import com.qiniu.qdownload.core.download.FileTask;

import org.junit.Test;

public class FileTaskTest {
    @Test
    public void initTest() {
        FileTask fileTask = new FileTask();
        fileTask.init();
    }
}
