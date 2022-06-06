package com.qiniu.qdownload.sample;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.download.Block;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class BlockTest {
    @Test
    public void getBlocksTest() {
        String url = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder(url).build();
        DownloadConnection.Response response = connection.execute();
        InputStream is = response.getInputStream();
        long contentLength = response.getContentLength();
        System.out.println("hk --------- contentLength " + contentLength);
        List<Block> blocks = Block.getBlocks(url,contentLength);
        System.out.println("hk --------- blocks " + blocks.size());


//        FileOutput fo = new FileOutput(new File("test1.html"), is);
//        fo.write();
    }
}
