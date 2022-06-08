package com.qiniu.qdownload.core.file;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.qiniu.qdownload.core.util.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class FileOutput {
    private static final String TAG = "FileOutput";
    private static final int BUFFER_SIZE = 1024;

    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private InputStream inputStream;
    private FileChannel fileChannel;
    private ParcelFileDescriptor pdf;

    public FileOutput(Context context, File file, InputStream inputStream) {
        Logger.DEFAULT.i(TAG, "create FileOutput : " + file.getAbsolutePath());

        Uri uri = Uri.fromFile(file);
        try {
            pdf = context.getContentResolver().openFileDescriptor(uri, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.inputStream = inputStream;
        this.fos = new FileOutputStream(pdf.getFileDescriptor());
        this.bos = new BufferedOutputStream(fos, BUFFER_SIZE);
        this.fileChannel = fos.getChannel();
    }

    public void seek(long position) {
        Logger.DEFAULT.i(TAG, "seek : " + position);

        try {
            fileChannel.position(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        int len;
        byte[] bytes = new byte[BUFFER_SIZE];
        long total = 0;
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                total += len;
            }
            bos.flush();
            Logger.DEFAULT.i(TAG, "write length : " + total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            bos.close();
            fos.close();
            pdf.close();
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
