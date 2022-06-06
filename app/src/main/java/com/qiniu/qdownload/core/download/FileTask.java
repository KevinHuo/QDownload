package com.qiniu.qdownload.core.download;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;

import java.io.InputStream;
import java.util.List;

public class FileTask {
    private String key;
    private int priority;
    private int jobs;
    private String url;
    private String etag;
    private int updateTime;
    private int prog;
    private int[] nab;
    private boolean wifi;

    public void init() {
//        String url = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";
//        String url = "http://p5mlo8b4j.bkt.clouddn.com/1-ZoeLiu-AOM%20-%20Open%20Source%20Video%20Codec.pdf";
        String url = "http://rd22mq0yl.hn-bkt.clouddn.com/test.csv";
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(url).build();
        DownloadConnection.Response response = connection.execute();
        InputStream is = response.getInputStream();
        long contentLength = response.getContentLength();
        System.out.println("hk --------- contentLength " + contentLength);
        List<Block> blocks = Block.getBlocks(url, contentLength);
        System.out.println("hk --------- blocks " + blocks.size());

        for (Block block : blocks) {
            new WorkTask(0, block).run();
        }

//        Block block = blocks.get(1);
//        new WorkTask(0, block).run();

//        for (Block block : blocks) {
//            new Thread(new WorkTask(0, block)).start();
//        }
    }
}
