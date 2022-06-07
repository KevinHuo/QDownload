package com.qiniu.qdownload.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qiniu.qdownload.core.download.FileTask;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final String URL1 = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";
    static final String URL2 = "http://p5mlo8b4j.bkt.clouddn.com/1-ZoeLiu-AOM%20-%20Open%20Source%20Video%20Codec.pdf";
    static final String URL3 = "http://rd22mq0yl.hn-bkt.clouddn.com/test.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileTask fileTask = new FileTask(MainActivity.this, URL3);
                fileTask.init();
            }
        }).start();
    }
}