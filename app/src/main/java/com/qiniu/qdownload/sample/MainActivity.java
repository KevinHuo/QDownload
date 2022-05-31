package com.qiniu.qdownload.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qiniu.qdownload.core.connection.DownloadConnection;
import com.qiniu.qdownload.core.connection.OKHttpDownloadConnection;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final String URL1 = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OKHttpDownloadConnection connection = new OKHttpDownloadConnection.Builder("https://1.1.1.1/").build();
                DownloadConnection.Response response = connection.execute();
            }
        }).start();
    }
}