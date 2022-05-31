package com.qiniu.qdownload.core.connection;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpURLDownloadConnection {
    private static final String TAG = "HttpURLDownloadConnection";

    private URL url;
    private Map<String, String> requestPropertyMap = new HashMap<>();
    private DownloadConnection.Response response;
    private HttpURLConnection urlConnection;
    private InputStream inputStream;

    HttpURLDownloadConnection(URL url, Map<String, String> requestPropertyMap) {
        this.url = url;
        this.requestPropertyMap = requestPropertyMap;
    }

    public DownloadConnection.Response execute() {
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            for (String key : requestPropertyMap.keySet()) {
                urlConnection.addRequestProperty(key, requestPropertyMap.get(key));
            }
            urlConnection.connect();

            response = new DownloadConnection.Response(urlConnection.getResponseCode(),
                    urlConnection.getResponseMessage(),
                    urlConnection.getContentLength(),
                    new BufferedInputStream(urlConnection.getInputStream()),
                    urlConnection.getHeaderFields()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public DownloadConnection.Response getResponse() {
        return response;
    }

    public void release() {
        urlConnection.disconnect();
    }

    public static class Builder {
        private URL url;
        private Map<String, String> requestPropertyMap = new HashMap<>();

        public Builder(String urlStr) {
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "MalformedURLException : urlStr = " + urlStr);
            }
        }

        public Builder setRequestProperty(@NonNull String key, @NonNull String value) {
            requestPropertyMap.clear();
            requestPropertyMap.put(key, value);
            return this;
        }

        public Builder addRequestProperty(@NonNull String key, @NonNull String value) {
            requestPropertyMap.put(key, value);
            return this;
        }

        public HttpURLDownloadConnection build() {
            return new HttpURLDownloadConnection(url, requestPropertyMap);
        }
    }
}
