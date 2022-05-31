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

    public OKHttpDownloadConnection(String url) {
        this.url = url;
    }

    public DownloadConnection.Response execute() {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response okResponse = client.newCall(request).execute();
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

