package com.qiniu.qdownload.core.download;

import android.content.Context;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.database.DatabaseHelper;
import com.qiniu.qdownload.core.module.FileTaskInfo;
import com.qiniu.qdownload.core.util.Logger;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FileTask implements Runnable, BlockDownloadListener {
    private static final String TAG = "FileTask";

    private Context context;
    private FileTaskInfo info;
    private String path;
    private List<Block> blocks;
    private ThreadPoolExecutor executor;
    private DatabaseHelper databaseHelper;

    public FileTask(Context context, String url, String path) {
        this.context = context;
        this.path = path;
        this.databaseHelper = new DatabaseHelper(this.context);

        this.info = new FileTaskInfo();
        info.setUrl(url);
        info.setKey(Integer.toString(url.hashCode()));

        this.executor = new ThreadPoolExecutor(3, info.getJobs(), 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void init() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(info.getUrl()).build();
        DownloadConnection.Response response = connection.execute();

        long contentLength = response.getContentLength();
        if (contentLength <= 0) {
            Logger.DEFAULT.e(TAG, "contentLength is <= 0, value is : " + contentLength);
            return;
        }
        info.setFileSize(contentLength);
        connection.close();

        blocks = Block.getBlocks(info.getUrl(), contentLength, path);
        info.initNab(blocks.size());

        databaseHelper.insert(info.getContentValues());

        Logger.DEFAULT.i(TAG, "contentLength : " + contentLength + ", blocks : " + blocks.size());

//        for (Block block : blocks) {
//            executor.execute(new WorkTask(context, 0, block, this));
//        }

        for (int i = 0; i < info.getJobs(); i++) {
            getBlock();
        }
    }

    private synchronized void getBlock() {
        Block next = nextBlock();
        if (next != null) {
            info.updateNab(next.getIndex(), FileTaskInfo.TASK_RUNNING);
            executor.execute(new WorkTask(context, 0, next, this));
        }
    }

    private synchronized Block nextBlock() {
        int index = info.getNextNabIndex();
        if (index != -1) {
            return blocks.get(index);
        }
        return null;
    }

    @Override
    public void run() {
    }

    @Override
    public void onCompleted(Block block) {
        info.updateNab(block.getIndex(),FileTaskInfo.TASK_DONE);
        getBlock();
    }
}
