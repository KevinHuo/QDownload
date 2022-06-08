package com.qiniu.qdownload.core.download;

public interface BlockDownloadListener {
    void onCompleted(Block block);
}
