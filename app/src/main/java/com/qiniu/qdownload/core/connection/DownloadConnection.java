package com.qiniu.qdownload.core.connection;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface DownloadConnection {
    class Response {
        private int code;
        private String message;
        private InputStream inputStream;
        private long contentLength;
        private Map<String, List<String>> responseHeaders;

        public Response(int code, String message, long contentLength, InputStream inputStream, Map<String, List<String>> responseHeaders) {
            this.code = code;
            this.message = message;
            this.contentLength = contentLength;
            this.inputStream = inputStream;
            this.responseHeaders = responseHeaders;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public long getContentLength() {
            return contentLength;
        }

        public Map<String, List<String>> getResponseHeaders() {
            return responseHeaders;
        }
    }
}
