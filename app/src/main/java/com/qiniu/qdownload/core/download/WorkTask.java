package com.qiniu.qdownload.core.download;


import android.content.Context;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.file.FileOutput;
import com.qiniu.qdownload.core.util.Logger;

import java.io.File;

public class WorkTask implements Runnable {
    private static final String TAG = "WorkTask";

    private int priority;
    private Block block;
    private Context context;

    public WorkTask(Context context, int priority, Block block) {
        Logger.DEFAULT.i(TAG, "create WorkTask , block : " + block.getRangeString());

        this.priority = priority;
        this.block = block;
        this.context = context;
    }

    @Override
    public void run() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(block.getUrl())
                .build();
        connection.addHeader("Range", block.getRangeString());
        DownloadConnection.Response response = connection.execute();

        if (response.getCode() == 206) {
            File file = new File(context.getFilesDir(), "c.csv");
            FileOutput fileOutput = new FileOutput(context, file, response.getInputStream());
            fileOutput.seek(block.getLeft());
            fileOutput.write();
            connection.close();
        } else {
            Logger.DEFAULT.e(TAG, "Not 206 , response code : " + response.getCode());
        }
    }
}
