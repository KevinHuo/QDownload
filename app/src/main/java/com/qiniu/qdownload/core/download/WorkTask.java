package com.qiniu.qdownload.core.download;


import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.file.FileOutput;

import java.io.File;

public class WorkTask implements Runnable {
    private int priority;
    private Block block;

    public WorkTask(int priority, Block block) {
        System.out.println("hk ----------- WorkTask " + block.getLeft());
        this.priority = priority;
        this.block = block;
    }

    @Override
    public void run() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(block.getUrl())
                .build();
        connection.addHeader("Range", block.getRangeString());
        DownloadConnection.Response response = connection.execute();

        if (response.getCode() == 206) {
            FileOutput fileOutput = new FileOutput(new File("c.csv"), response.getInputStream());
            fileOutput.seek(block.getLeft());
//            fileOutput.seek(0);
            fileOutput.write();
            connection.close();
        } else {
            System.out.println("hk ----------------------- error : " + response.getCode());
        }
    }
}
