package com.qiniu.qdownload.sample;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.HttpURLDownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;

import org.junit.Test;

import java.io.InputStream;

public class ConnectionTest {
    @Test
    public void okHttpTest() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder("http://1.1.1.1/").build();
        DownloadConnection.Response response = connection.execute();
    }

    @Test
    public void httpURLTest() {
        HttpURLDownloadConnection connection = new HttpURLDownloadConnection.Builder("http://1.1.1.1/").build();
        DownloadConnection.Response response = connection.execute();
    }

    @Test
    public void fileTest() {
        OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder("https://www.qiniu.com/").build();
        DownloadConnection.Response response = connection.execute();
        InputStream is = response.getInputStream();
    }
}