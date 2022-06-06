package com.qiniu.qdownload.core.connection;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpDownloadConnection {
    OkHttpClient client = new OkHttpClient();
    private String url;
    DownloadConnection.Response response;
    private Request.Builder builder;

    public OKHttpDownloadConnection(String url) {
        this.url = url;
        this.builder = new Request.Builder().url(url);
    }

    public void addHeader(String key, String value) {
        builder.addHeader(key, value);
    }

    public DownloadConnection.Response execute() {
        try {
            Response okResponse = client.newCall(builder.build()).execute();
            response = new DownloadConnection.Response(okResponse.code(),
                    okResponse.message(),
                    okResponse.body().contentLength(),
                    okResponse.body().byteStream(),
                    okResponse.headers().toMultimap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        if (response != null) {
            try {
                response.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Builder {
        String url;

        public Builder(@NonNull String url) {
            this.url = url;
        }

        public OKHttpDownloadConnection build() {
            return new OKHttpDownloadConnection(url);
        }
    }
}

