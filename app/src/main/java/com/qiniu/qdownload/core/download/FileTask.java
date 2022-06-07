package com.qiniu.qdownload.core.download;

import android.content.Context;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.util.Logger;

import java.util.List;

public class FileTask {
    private static final String TAG = "FileTask";

    private String key;
    private int priority;
    private int jobs;
    private String url;
    private String etag;
    private int updateTime;
    private int prog;
    private int[] nab;
    private boolean wifi;

    private Context context;

    public FileTask(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public void init() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(url).build();
        DownloadConnection.Response response = connection.execute();
        long contentLength = response.getContentLength();
        if (contentLength <= 0) {
            Logger.DEFAULT.e(TAG, "contentLength is <= 0, value is : " + contentLength);
            return;
        }
        List<Block> blocks = Block.getBlocks(url, contentLength);
        Logger.DEFAULT.i(TAG, "contentLength : " + contentLength + ", blocks : " + blocks.size());

        for (Block block : blocks) {
            new WorkTask(context, 0, block).run();
        }
    }
}
