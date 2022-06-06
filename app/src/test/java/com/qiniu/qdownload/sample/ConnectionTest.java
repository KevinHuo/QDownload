package com.qiniu.qdownload.sample;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.HttpURLDownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;
import com.qiniu.qdownload.core.file.FileOutput;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConnectionTest {
    @Test
    public void okHttpTest() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder("http://1.1.1.1/").build();
        DownloadConnection.Response response = connection.execute();
        System.out.println("hk -------------  : " + response.getCode());
    }

    @Test
    public void httpURLTest() {
        HttpURLDownloadConnection connection = new HttpURLDownloadConnection.Builder("http://1.1.1.1/").build();
        DownloadConnection.Response response = connection.execute();
        System.out.println("hk -------------  : " + response.getCode());
    }

    @Test
    public void fileTest() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder("https://www.qiniu.com/").build();
        DownloadConnection.Response response = connection.execute();
        InputStream is = response.getInputStream();

        FileOutput fo = new FileOutput(new File("test1.html"), is);
        fo.write();

    }
}